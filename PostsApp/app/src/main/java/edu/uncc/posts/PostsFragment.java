package edu.uncc.posts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.uncc.posts.databinding.FragmentPostsBinding;
import edu.uncc.posts.databinding.PostRowItemBinding;
import edu.uncc.posts.models.AuthResponse;
import edu.uncc.posts.models.Post;
import edu.uncc.posts.models.PostsResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPref;
    String token;
    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();
    long current_page;
    long num_pages;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        token = sharedPref.getString(getString(R.string.my_app_token),"");
        binding.textViewTitle.setText("Welcome " +  sharedPref.getString(getString(R.string.my_app_username),""));


        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createPost();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter();
        binding.recyclerViewPosts.setAdapter(postsAdapter);

        current_page = 1;
        getmPosts(current_page);
        postsAdapter.notifyDataSetChanged();

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_page >1){
                    getmPosts(current_page - 1);
                }
            }
        });

        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_page < num_pages) {
                    getmPosts(current_page + 1);
                }
            }
        });

        getActivity().setTitle(R.string.posts_label);
    }

    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PostRowItemBinding binding = PostRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.setupUI(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder {
            PostRowItemBinding mBinding;
            Post mPost;
            public PostsViewHolder(PostRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Post post){
                mPost = post;
                mBinding.textViewPost.setText(post.getPost_text());
                mBinding.textViewCreatedBy.setText(post.getCreated_by_name());
                mBinding.textViewCreatedAt.setText(post.getCreated_at());
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.newCall(deletePost(mPost.getPost_id())).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if (response.isSuccessful()){
                                    getmPosts(current_page);
                                } else {
                                    if (Looper.myLooper() ==  null){
                                        Looper.prepare();
                                    }
                                    Gson gson = new Gson();
                                    AuthResponse authResponse = gson.fromJson(response.body().string(), AuthResponse.class);
                                    Toast.makeText(getActivity(), authResponse.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }

    }

    PostsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    interface PostsListener{
        void logout();
        void createPost();
    }

    private void getmPosts(long page){
        binding.textViewPaging.setText("Loading ...");


        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/posts").newBuilder()
                .addQueryParameter("page", String.valueOf(page)).build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "BEARER " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    mPosts.clear();
                    String body = response.body().string();
                    Gson gson = new Gson();

                    PostsResponse response1 =  gson.fromJson(body, PostsResponse.class);

                    mPosts.addAll(response1.getPosts());

                    num_pages = Integer.valueOf(response1.getTotalCount())/Integer.valueOf(response1.getPageSize());

                    getActivity().runOnUiThread(() -> {
                        binding.textViewPaging.setText("Showing page " + String.valueOf(page) + "of " + String.valueOf(num_pages));
                        postsAdapter.notifyDataSetChanged();
                        current_page = page;
                    });
                } else {
                    getActivity().runOnUiThread(() -> {
                        binding.textViewPaging.setText("Error loading data");
                    });
                }
            }
        });
    }

    private Request deletePost(String post_id){

        FormBody formBody = new FormBody.Builder()
                .add("post_id", post_id)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/delete")
                .addHeader("Authorization", "BEARER " + token)
                .post(formBody)
                .build();

        return request;
    }
}
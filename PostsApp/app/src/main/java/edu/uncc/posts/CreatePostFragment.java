package edu.uncc.posts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import edu.uncc.posts.databinding.FragmentCreatePostBinding;
import edu.uncc.posts.models.AuthResponse;
import edu.uncc.posts.models.PostsResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreatePostFragment extends Fragment {
    public CreatePostFragment() {
        // Required empty public constructor
    }

    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPref;
    String token;
    FragmentCreatePostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        token = sharedPref.getString(getString(R.string.my_app_token),"");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToPosts();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postText = binding.editTextPostText.getText().toString();
                if(postText.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid post !!", Toast.LENGTH_SHORT).show();
                } else {
                    client.newCall(deletePost(postText)).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()){
                                mListener.goBackToPosts();
                            } else {
                                Gson gson = new Gson();
                                AuthResponse authResponse = gson.fromJson(response.body().string(), AuthResponse.class);
                                Toast.makeText(getActivity(), authResponse.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        getActivity().setTitle(R.string.create_post_label);
    }

    CreatePostListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreatePostListener) context;
    }

    interface CreatePostListener {
        void goBackToPosts();
    }

    private Request deletePost(String post_text){

        FormBody formBody = new FormBody.Builder()
                .add("post_text", post_text)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts/create")
                .addHeader("Authorization", "BEARER " + token)
                .post(formBody)
                .build();

        return request;
    }
}
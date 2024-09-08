// Assignment03
// MainActivity.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment03;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewFindAllText;
    private final int[] imageViewFruitIds = {R.id.imageView, R.id.imageView2, R.id.imageView3, R.id.imageView4,
            R.id.imageView5, R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView9, R.id.imageView10,
            R.id.imageView11, R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15, R.id.imageView16,
            R.id.imageView17, R.id.imageView18, R.id.imageView19, R.id.imageView20, R.id.imageView21, R.id.imageView22,
            R.id.imageView23, R.id.imageView24, R.id.imageView25};
    private final int[] drawableFruitIds = {R.drawable.apple, R.drawable.lemon, R.drawable.mango, R.drawable.peach,
            R.drawable.strawberry, R.drawable.tomato};
    private Random random = new Random();
    private int focusImageId;
    String focusImageName;
    private int requiredFocusCount;
    private HashSet<Integer> selectedImages = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewFindAllText = findViewById(R.id.findAllFruitsText);

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupNewGame();
            }
        });
        setupNewGame();
    }

    private void setupNewGame(){
        selectedImages.clear();

        int focusIndex = random.nextInt(drawableFruitIds.length);
        focusImageId = drawableFruitIds[focusIndex];
        requiredFocusCount = random.nextInt(8) + 1;

        ArrayList<Integer> allImages = new ArrayList<>();

        for (int i = 0; i < requiredFocusCount; i++) {
            allImages.add(focusImageId);
        }

        for (int i = 0; i < 25 - requiredFocusCount; i++) {
            int randomNonFocusIndex;
            do {
                randomNonFocusIndex = random.nextInt(drawableFruitIds.length);
            } while (randomNonFocusIndex == focusIndex);
            allImages.add(drawableFruitIds[randomNonFocusIndex]);
        }

        Collections.shuffle(allImages);

        for (int i = 0; i < imageViewFruitIds.length; i++) {
            ImageView imageView = findViewById(imageViewFruitIds[i]);
            imageView.setImageResource(allImages.get(i));
            imageView.setAlpha(1.0f);
            imageView.setTag(allImages.get(i));
            imageView.setOnClickListener(this);
        }

        String[] fruitNames = {"Apples", "Lemons", "Mangoes", "Peaches", "Strawberries", "Tomatoes"};
        focusImageName = fruitNames[focusIndex];
        textViewFindAllText.setText("Find All " + focusImageName + " (" + requiredFocusCount +")");
    }

    @Override
    public void onClick(View view) {
        ImageView imageView = (ImageView) view;
        int clickedImageId = (int) imageView.getTag();

        if (clickedImageId == focusImageId) {
            if (!selectedImages.contains(imageView.getId())) {
                selectedImages.add(imageView.getId());
                imageView.setAlpha(0.5f);
                imageView.setOnClickListener(null);
                requiredFocusCount--;

                textViewFindAllText.setText("Find All " + focusImageName + " (" + requiredFocusCount +")");

                shuffleUnselectedImages();

                if (requiredFocusCount == 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("Found All Shapes")
                            .setMessage("Congratulations! You have found all the " + focusImageName + "!")
                            .setPositiveButton("Ok", (dialog, which) -> setupNewGame())
                            .setCancelable(false)
                            .show();
                }
            }
        }
    }

    private void shuffleUnselectedImages() {
        ArrayList<Integer> remainingImages = new ArrayList<>();

        for (int i = 0; i < imageViewFruitIds.length; i++) {
            ImageView imageView = findViewById(imageViewFruitIds[i]);
            if (!selectedImages.contains(imageView.getId())) {
                remainingImages.add((int) imageView.getTag());
            }
        }

        Collections.shuffle(remainingImages);

        int j = 0;
        for (int i = 0; i < imageViewFruitIds.length; i++) {
            ImageView imageView = findViewById(imageViewFruitIds[i]);
            if (!selectedImages.contains(imageView.getId())) {
                imageView.setImageResource(remainingImages.get(j));
                imageView.setTag(remainingImages.get(j));
                j++;
            }
        }
    }
}
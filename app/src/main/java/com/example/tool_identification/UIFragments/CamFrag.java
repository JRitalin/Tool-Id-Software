package com.example.tool_identification.UIFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tool_identification.R;
import com.example.tool_identification.ToolIdentification.DetectObject;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Mode;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class CamFrag extends Fragment {

    private static final String TAG = "Test";

    Button btnCapture,btnBack;
    ImageView imageView;
    TextView feature,probability;
    CameraView cameraView;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    DetectObject detectObject;
    Context context;



    public CamFrag(){
        // Required Empty Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_camera,container,false);

        context = getContext();

        imageView = view.findViewById(R.id.imageView);
        feature = view.findViewById(R.id.feature);
        probability = view.findViewById(R.id.probability);
        btnCapture = view.findViewById(R.id.takePhotoButton);
        btnCapture.setVisibility(View.VISIBLE);
        btnBack = view.findViewById(R.id.returnButton);
        btnBack.setVisibility(View.GONE);

        surfaceView = view.findViewById(R.id.surfaceView);
        surfaceView.setZOrderOnTop(true);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);

        cameraView = view.findViewById(R.id.camera);
        cameraView.setMode(Mode.PICTURE);
        cameraView.setLifecycleOwner(this);


        btnCapture.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);

        return view;
    }

    // take picture and listen for results
    public void takePicture(){
        cameraView.addCameraListener(new CameraListener() {

            @Override

            public void onPictureTaken(@NotNull PictureResult result) {
                result.toBitmap(320, 320, bitmap -> {
                    imageView.setImageBitmap(bitmap);
                    detectObject = new DetectObject(bitmap,surfaceHolder);
                    detectObject.setStream(false);
                    detectObject.setContext(context);
                    detectObject.Detect();
                    feature.setText(detectObject.getFeature());
                    probability.setText(detectObject.getFinalSize());
                    cameraView.close();
                });
            }
        });
        cameraView.takePicture();
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }


    private void onClick(View v) {
        if (cameraView.isOpened()) {
            CamFrag.this.takePicture();
            Log.d(TAG, "Picture Taken");
            btnCapture.setVisibility(View.GONE);
            btnBack.setVisibility(View.VISIBLE);
        } else {
            cameraView.open();
            btnBack.setVisibility(View.GONE);
            btnCapture.setVisibility(View.VISIBLE);

        }
    }
}
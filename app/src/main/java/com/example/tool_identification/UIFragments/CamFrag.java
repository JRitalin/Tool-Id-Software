package com.example.tool_identification.UIFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.RectF;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tool_identification.R;
import com.example.tool_identification.ToolIdentification.DetectObject;
import com.example.tool_identification.ToolIdentification.DrawBoundingBox;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import org.jetbrains.annotations.NotNull;

public class CamFrag extends Fragment {

    private static final String TAG = "Test";

    public ImageView imageView;
    public TextView featureText, confidenceText;

    private Button btnCapture, btnBack;
    private CameraView cameraView;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private DetectObject detectObject;
    private Context context;
    private Bitmap bitmap;
    private RectF Rbounds;
    private DrawBoundingBox drawBoundingBox;


    public CamFrag() {
        // Required Empty Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }// End of onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_camera, container, false);

        context = getContext();

        // Sets up Camera screen
        imageView = view.findViewById(R.id.imageView);
        featureText = view.findViewById(R.id.feature);
        confidenceText = view.findViewById(R.id.probability);
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
        cameraView.addFrameProcessor(new FrameProcessor() {
            @Override
            public void process(@NonNull Frame frame) {
                Rbounds = new RectF(827,10,100,422);
                drawBoundingBox = new DrawBoundingBox(surfaceHolder,Rbounds);
                drawBoundingBox.DrawPersistentBox();
            }
        });
        // Listeners for the buttons.  See the onClick for functionality
        btnCapture.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);

        return view;
    }// End of onCreateView

    // Take picture and listen for results
    public void takePicture() {
        cameraView.addCameraListener(new CameraListener() {

            @Override
            public void onPictureTaken(@NotNull PictureResult result) {
                result.toBitmap(1080, 1440, new BitmapCallback() {
                    @Override
                    public void onBitmapReady(@Nullable Bitmap bitmap) {

                        bitmap = rotateImage(bitmap,180);
                        imageView.setImageBitmap(bitmap);
                        detectObject = new DetectObject(bitmap, surfaceHolder);
                        detectObject.setStream(true);
                        detectObject.setContext(context);
                        detectObject.Detect();
                        featureText.setText(detectObject.getFeature());
                        confidenceText.setText(detectObject.getFinalSize());
                        cameraView.close();
                    }
                });
            }
        });
        cameraView.takePicture();
    }// End of takePicture


    // Method onClick:
    private void onClick(View v) {
        if (cameraView.isOpened()) {
            CamFrag.this.takePicture();
            Log.d(TAG, "Picture Taken");
            // When photo is taken, sets btnCapture invisible and makes btnBack visible
            btnCapture.setVisibility(View.GONE);
            btnBack.setVisibility(View.VISIBLE);
        } else {
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            cameraView.open();
            // When back button is pressed, sets btnBack invisible and btnCapture visible
            btnBack.setVisibility(View.GONE);
            btnCapture.setVisibility(View.VISIBLE);

        }
    }// End of onClick

    // Override Camera methods
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
    // End of Override Camera methods


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}

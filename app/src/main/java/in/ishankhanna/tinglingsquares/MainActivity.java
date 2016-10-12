package in.ishankhanna.tinglingsquares;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etLagFactor, etAnimTime;
    Button btUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TinglingSquaresView tsv = (TinglingSquaresView) findViewById(R.id.tinglingSquaresView);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tsv.runAnimation(0);
            }
        });

        etAnimTime = (EditText) findViewById(R.id.et_base_anim_time);
        etLagFactor = (EditText) findViewById(R.id.et_lag_factor);
        btUpdate = (Button) findViewById(R.id.button2);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean somethingChanged = false;
                String animInput = etAnimTime.getEditableText().toString();
                if (!animInput.isEmpty()) {
                    int animTime = Integer.parseInt(animInput);
                    if (animTime > 0) {
                        TinglingSquaresView.setAnimationTimeBase(animTime);
                        somethingChanged = true;
                    }
                }

                String lagInput = etLagFactor.getEditableText().toString();
                if (!lagInput.isEmpty()) {
                    float lagFactor = Float.parseFloat(lagInput);
                    if (lagFactor > 0f && lagFactor < 1f) {
                        TinglingSquaresView.setLagFactor(lagFactor);
                        somethingChanged = true;
                    }
                }

                if (somethingChanged) {
                    tsv.initAnimations();
                }

            }
        });
    }




}

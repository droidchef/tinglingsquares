package in.ishankhanna.tinglingsquares;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TinglingSquaresView tsv = (TinglingSquaresView) findViewById(R.id.tinglingSquaresView);
        tsv.runAnimation(0);
    }

}

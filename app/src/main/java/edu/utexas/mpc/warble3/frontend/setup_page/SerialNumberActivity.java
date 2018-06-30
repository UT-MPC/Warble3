package edu.utexas.mpc.warble3.frontend.setup_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import edu.utexas.mpc.warble3.R;

public class SerialNumberActivity extends AppCompatActivity {
    private EditText serialNumber_editText;
    private Button serialNumberSend_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_number);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.titleSerialNumber_addLightPage);

        serialNumber_editText = findViewById(R.id.serialNumber_editText);
        serialNumberSend_button = findViewById(R.id.serialNumberSend_button);
        serialNumberSend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT", serialNumber_editText.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}

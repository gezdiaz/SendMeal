package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button registerBtn;
    EditText nameEdit, passwordEdit, repeatPasswordEdit, emailEdit, cardNumberEdit, ccvEdit, dateEdit, cbuAliasEdit, cbuNumberEdit;
    TextView showAmountView;
    RadioGroup accountRadioGroup;
    SeekBar initialCreditSlider;
    ToggleButton sendNotfBtn;
    Switch isSellerSw;
    CheckBox acceptTermsCheck;
    LinearLayout layoutAccount;
    boolean validations[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = findViewById(R.id.registerBtn);
        nameEdit = findViewById(R.id.nameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        repeatPasswordEdit = findViewById(R.id.repeatPasswordEdit);
        emailEdit = findViewById(R.id.emailEdit);
        cardNumberEdit = findViewById(R.id.cardNumberEdit);
        ccvEdit = findViewById(R.id.ccvEdit);
        dateEdit = findViewById(R.id.dateEdit);
        showAmountView = findViewById(R.id.showAmount);
        accountRadioGroup = findViewById(R.id.accountRadioGroup);
        initialCreditSlider = findViewById(R.id.initialCreditSlider);
        sendNotfBtn = findViewById(R.id.sendNotifBtn);
        isSellerSw = findViewById(R.id.isSellerSw);
        acceptTermsCheck = findViewById(R.id.acceptTermsCheck);
        cbuAliasEdit = findViewById(R.id.CBUAliasEdit);
        cbuNumberEdit = findViewById(R.id.CBUNumberEdit);
        layoutAccount = findViewById(R.id.layoutAccount);

        validations = new boolean[9];

        //Integer validations = 0; //Va a ser 9 si todos son válidos.


        nameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(nameEdit.getText().toString().isEmpty()){
                        nameEdit.setError(getString(R.string.errorEmptyField));
                        validations[0] = false;
                    }else{
                        //Válido
                        validations[0] = true;
                    }
                }
            }
        });

        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(passwordEdit.getText().toString().isEmpty()){
                        passwordEdit.setError(getString(R.string.errorEmptyPassword));
                        validations[1]=false;
                    }else{
                        //Válido
                        validations[1]=true;
                    }
                }
            }
        });
        repeatPasswordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(!repeatPasswordEdit.getText().toString().equals(passwordEdit.getText().toString())){
                        repeatPasswordEdit.setError(getString(R.string.errorRepeatPassword));
                        validations[2]=false;
                    }else{
                        //Válido
                        validations[2]=true;
                    }
                }
            }
        });
        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(emailEdit.getText().toString().isEmpty()){
                        emailEdit.setError(getString(R.string.errorEmptyField));
                        validations[3]=false;
                    }else{
                        //Válido
                        validations[3]=true;
                    }
                }
            }
        });
        cardNumberEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(cardNumberEdit.getText().toString().isEmpty()){
                        cardNumberEdit.setError(getString(R.string.errorEmptyField));
                        validations[4]=false;
                    }else{
                        //Válido
                        validations[4]=true;
                    }
                }
            }
        });
        ccvEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(ccvEdit.getText().toString().isEmpty()){
                        ccvEdit.setError(getString(R.string.errorEmptyField));
                        validations[5]=false;
                    }else{
                        //Válido
                        validations[5]=true;
                    }
                }
            }
        });
        dateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(dateEdit.getText().toString().isEmpty()){
                        dateEdit.setError(getString(R.string.errorEmptyField));
                        validations[6]=false;
                    }else{
                        //Válido
                        validations[6]=true;
                    }
                }
            }
        });

        initialCreditSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                showAmountView.setText("$ " + (100+progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        isSellerSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChk) {
                if(isChk){
                    layoutAccount.setVisibility(View.VISIBLE);
                }else{
                    layoutAccount.setVisibility(View.GONE);
                }
            }
        });
        cbuAliasEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    if (cbuAliasEdit.getText().toString().isEmpty()) {
                        cbuAliasEdit.setError(getString(R.string.errorEmptyField));

                        validations[7] = false;
                    } else {
                        //Válido
                        validations[7] = true;
                    }
                }
            }
        });
        cbuNumberEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(cbuNumberEdit.getText().toString().isEmpty()){
                        cbuNumberEdit.setError(getString(R.string.errorEmptyField));
                        validations[8]=false;
                    }else{
                        //Válido
                        validations[8]=true;
                    }
                }
            }
        });
        acceptTermsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChk) {
                if(isChk){
                    registerBtn.setEnabled(true);
                }else{
                    registerBtn.setEnabled(false);
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEdit.clearFocus();
                passwordEdit.clearFocus();
                repeatPasswordEdit.clearFocus();
                emailEdit.clearFocus();
                cardNumberEdit.clearFocus();
                ccvEdit.clearFocus();
                dateEdit.clearFocus();
                cbuAliasEdit.clearFocus();
                cbuNumberEdit.clearFocus();

                registerBtn.requestFocus();
                boolean valid = true;
                Context context = getApplicationContext();
                Toast toast;

                for(int i = 0; i < validations.length; i++){
                    if(!validations[i]){
                        if(!isSellerSw.isChecked() && (i == 7 || i == 8)){
                            continue;
                        }
                        valid = false;
                    }
                }
                if(valid){
                    toast = Toast.makeText(context,getString(R.string.successToast),Toast.LENGTH_SHORT);
                }else{
                    toast = Toast.makeText(context,getString(R.string.errorToast),Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });



    }
}

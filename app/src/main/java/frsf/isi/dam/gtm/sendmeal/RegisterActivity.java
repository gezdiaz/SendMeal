package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
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

;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText nameEdit, passwordEdit, repeatPasswordEdit, emailEdit, cardNumberEdit, ccvEdit, dateEdit, cbuAliasEdit, cbuNumberEdit;
    private TextView showAmountView;
    private RadioGroup accountRadioGroup;
    private SeekBar initialCreditSlider;
    private ToggleButton sendNotfBtn;
    private Switch isSellerSw;
    private  CheckBox acceptTermsCheck;
    private LinearLayout layoutAccount;
    private boolean validations[];
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        toolbar = findViewById(R.id.registerToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

                        if(!emailIsValid(emailEdit.getText().toString())){
                            emailEdit.setError(getString(R.string.emailInvalid));
                        }
                        else {
                            //Válido
                            validations[3] = true;
                        }
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
                            validations[5] = true;
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
                        //Si o si tengo que validar que la longitud sea cinco porque tambien acepta fechas del tipo dd/mm/aaaaa
                        if(dateEdit.getText().toString().length() == 5 && dateEdit.getText().toString().charAt(2) == '/') {
                            Calendar currentDate = Calendar.getInstance();
                            try {
                                Integer month = Integer.valueOf(dateEdit.getText().toString().substring(0, 2));
                                Integer year = Integer.valueOf(dateEdit.getText().toString().substring(3));
                                if (month >= 1 && month <= 12 && year >= 19 && year <= 99) {
                                        if (!dateIsValid(month, year, currentDate)) {
                                            dateEdit.setError(getString(R.string.errorInvalidCardDate));
                                            validations[6] = false;
                                        } else {
                                            //Válido
                                            validations[6] = true;
                                        }
                                } else {
                                        dateEdit.setError(getString(R.string.errorInvalidCardDate));
                                        validations[6] = false;
                                    }
                                    } catch (Exception e){
                                   dateEdit.setError(getString(R.string.errorInvalidCardDate));
                                   validations[6] = false;
                                    }

                            } else {
                                dateEdit.setError(getString(R.string.errorInvalidCardDate));
                                validations[6] = false;
                            }
                        }
                    }
                }
           // }
        });

        accountRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioBtnSelectedId) {
               switch(radioBtnSelectedId){
                   case (R.id.basicAccountRadio):{
                       initialCreditSlider.setProgress(0);
                       break;
                   }
                   case (R.id.premiumAccountRadio):{
                       initialCreditSlider.setProgress(150);
                       break;
                   }
                   case  (R.id.fullAccountRadio):{
                       initialCreditSlider.setProgress(400);
                       break;
                   }
                   default:{
                       break;
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
                    //TODO registrar usuario
                }else{
                    toast = Toast.makeText(context,getString(R.string.errorToast),Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });



    }
    private Boolean emailIsValid(String email){
        if(email.contains("@") && (email.indexOf("@") != 0) && (email.substring(email.indexOf("@")).length() > 3)){
            return true;
        }else{
            return false;
        }
    }

    //Metodo para validar la fecha de la tarjeta con el dato directo del editText
    private Boolean dateIsValid(Integer enteredMonth, Integer enteredYear, Calendar currentDate){
        Boolean result = false;

        if (((enteredYear + 2000) == currentDate.get(Calendar.YEAR))) {
            if ((enteredMonth - (currentDate.get(Calendar.MONTH) + 1)) >= 3) {
                result = true;
            }
            else {
                result = false;
            }
        }
        else {
            if (enteredYear + 2000 > currentDate.get(Calendar.YEAR)) {

                if ((enteredMonth == 1 && ((currentDate.get(Calendar.YEAR) + 1) > 10)) || (enteredMonth == 2 && ((currentDate.get(Calendar.YEAR) + 1) > 11))) {
                    result = false;
                }
                else {
                    result = true;
                }
            }
            else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                Toast.makeText(this, "....", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}

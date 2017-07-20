package ht.ihsi.inventaireterrain.Views.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import ht.ihsi.inventaireterrain.Constant.Constant;
import ht.ihsi.inventaireterrain.Models.PersonnelModel;
import ht.ihsi.inventaireterrain.R;
import ht.ihsi.inventaireterrain.Utilities.Shared_Preferences;
import ht.ihsi.inventaireterrain.Utilities.ToastUtility;
import ht.ihsi.inventaireterrain.Utilities.Tools;
import ht.ihsi.inventaireterrain.Views.MainActivity;

public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener, View.OnClickListener {
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    public BootstrapButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            init(Constant.FORM_DATA_MANAGER);
            init(Constant.CU_RECORD_MANAGER);

            // Set up the login form.
            mEmailView = (EditText) findViewById(R.id.tv_UserName);
            mPasswordView = (EditText) findViewById(R.id.tv_Password);
            mPasswordView.setOnEditorActionListener(this);

            //Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            //progressGenerator = new ProgressGenerator(this);
            btnSignIn = (BootstrapButton) findViewById(R.id.btnSignIn);
            btnSignIn.setOnClickListener(this);
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-onCreate(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        try{
            if (actionId == R.id.imeActionLabel_SignIn || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
                LoginConnexion();
                return true;
            }
        }catch (Exception ex) {
            ToastUtility.LogCat("Exception-onEditorAction(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnSignIn:
                    LoginConnexion();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            ToastUtility.LogCat("Exception-onClick(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    private void LoginConnexion() {
        try {
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean isValide = true;
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                isValide = false;
                message = getString(R.string.label_CompteUtilisateur) + " " + getString(R.string.msg_Obligatoire);
                Tools.SetErrorField(this, mEmailView, message);
            } else if (TextUtils.isEmpty(password)) {
                isValide = false;
                message = getString(R.string.label_MotDePasse) + " " + getString(R.string.msg_Obligatoire);
                Tools.SetErrorField(this, mPasswordView, message);
            }

            if (isValide) {
                PersonnelModel personnelModel = formDataMngr.getPersonnelInfo(email, password);
                if(personnelModel != null ) {
                    if( !personnelModel.getEstActif()){
                        message = getString(R.string.label_CompteUtilisateur) + " " + getString(R.string.msg_Inactif);;
                        Tools.SetErrorField(this, mEmailView, message);
                    }else {
                        personnelModel.setIsConnected(true);
                        Tools.StoreInfoPresonnel_PreferenceManager(this, personnelModel);

                        // On desactive les autres compte Utilisateur des agent
                        // On verifier bien que la tablette est bien configurer
                        Shared_Preferences SPref = Tools.SharedPreferences(this);
                        if( SPref != null && SPref.getIsConfigured() ) { // Si le formulaire est deja configurer sur la tablette
                            if( Constant.COMPTE_AGENT == SPref.getProfileId() ){ // Si c'est un compte Agent
                                PersonnelModel personnel = null;
                                personnel = cuRecordMngr.updateAllPersonnel(personnelModel.getPersId());
                            }
                        }

                        message =  "Bienvenu: " + personnelModel.getPrenom() +" "+ personnelModel.getNom();
                        ToastUtility.LogCat(message);
                        ToastUtility.ToastMessage(this, message, Constant.GravityBottom);

                        finish();
                        //intent = new Intent(this, MainActivity.class);
                        //startActivity(intent);
                    }
                }else{
                    message = getString(R.string.label_CompteUtilisateur) + " et/ou " + getString(R.string.label_MotDePasse)
                            + " " + getString(R.string.label_Incorrect);
                    Tools.SetErrorField(this, mEmailView, message);
                }
            }
        }catch (Exception ex) {
            Disconnected();
            ToastUtility.LogCat("Exception-LoginConnexion(): getMessage: " + ex.getMessage() + " \n toString: " + ex.toString());
            ex.printStackTrace();
        }
    }
    private void Disconnected() {
        PersonnelModel personnelModel = new PersonnelModel();
        personnelModel.setIsConnected(false);
        Tools.StoreInfoPresonnel_PreferenceManager(this, personnelModel);
    }
}

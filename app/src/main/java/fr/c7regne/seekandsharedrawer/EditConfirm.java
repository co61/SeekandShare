package fr.c7regne.seekandsharedrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditConfirm extends AppCompatDialogFragment {
    private EditConfirmListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Editer")
                .setMessage("Voulez-vous vraiment éditer votre annonce ?")
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesEditClikcked();
                    }
                });
        return builder.create();
    }

    public interface EditConfirmListener{
        void onYesEditClikcked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditConfirmListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implements DeleteConfirmListener");
        }


    }
}

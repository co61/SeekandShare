package fr.c7regne.seekandsharedrawer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteConfirm extends AppCompatDialogFragment {
    private DeleteConfirmListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Supprimer")
                .setMessage("Voulez-vous vraiment supprimer votre annonce ?")
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesDeleteClikcked();
                    }
                });
        return builder.create();
    }

    public interface DeleteConfirmListener{
        void onYesDeleteClikcked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteConfirmListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implements DeleteConfirmListener");
        }


    }
}

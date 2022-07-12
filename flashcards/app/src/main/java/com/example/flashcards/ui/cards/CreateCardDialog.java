package com.example.flashcards.ui.cards;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.flashcards.CardsBank;
import com.example.flashcards.R;
import com.example.flashcards.database.entity.FlashCard;

public class CreateCardDialog extends DialogFragment {

    private EditText mWordEditText;
    private EditText mValueEditText;
    private Button mBtnAdd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");

        View dialogView = inflater.inflate(R.layout.fragment_create_card, null);

        mBtnAdd = dialogView.findViewById(R.id.btnAdd);
        mBtnAdd.setOnClickListener(this::onClick);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this::onClick);
        mWordEditText = dialogView.findViewById(R.id.create_card_et_word);
        mValueEditText = dialogView.findViewById(R.id.create_card_et_value);

        return dialogView;
    }

    public void onClick(View v) {
        Button btn = (Button) v;
        CharSequence text = btn.getText();
        if (mBtnAdd.getText().equals(text)) {
            String word = mWordEditText.getText().toString();
            String value = mValueEditText.getText().toString();
            ;

            //--- если слово или значение пусты, то выводим сообщение об этом и не создаём новый объект
            if (word.trim().length() == 0 || value.trim().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.attention)
                        .setMessage(R.string.msgErrorCreateCard)
                        .setPositiveButton(R.string.accept, null)
                        .create()
                        .show();
            } else {
                CardsBank.get().addCard(new FlashCard(word, value));
                Toast msg = Toast.makeText(getActivity(), R.string.addCardSuccess, Toast.LENGTH_SHORT);
                msg.show();
            }
        }

        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

}

package com.example.s05volunteer;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

// Generates dialogs so it doesn't clog the Activities
// Apparently our design has the most complex user flow ever with 6 different dialogs that chain into each other
// Thanks Aki
// Haha ur welcome :)
public final class DialogManager {
    Context context;
    ClickListener clickListener;

    public DialogManager(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    // Opportunity details dialog. Flow typically starts here
    public void startDialogChain(VolunteerOpportunity item) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_details);
        dialog.setCancelable(false);

        // Link the XML
        TextView name = dialog.findViewById(R.id.text_name);
        TextView description = dialog.findViewById(R.id.text_desc);
        TextView details = dialog.findViewById(R.id.text_details);
        TextView date = dialog.findViewById(R.id.text_date);
        TextView location = dialog.findViewById(R.id.text_location);
        TextView email = dialog.findViewById(R.id.text_email);
        ImageView logo = dialog.findViewById(R.id.image_logo);
        ImageView org = dialog.findViewById(R.id.image_org);
        Button button = dialog.findViewById(R.id.button);
        Button cancel = dialog.findViewById(R.id.button_close);

        // Set known values
        name.setText(item.name);
        description.setText(item.description);
        details.setText(item.details);
        date.setText(item.date);
        location.setText(item.location);
        email.setText(item.email);
        logo.setImageResource(item.logoDrawable);
        org.setImageResource(item.orgDrawable);

        // Confirm button
        if (item.isRegistered) {
            // We can also use this dialog to cancel registrations by editing the layout a bit
            // This probably causes a memory leak?
            button.setBackgroundColor(context.getColor(R.color.red));
            button.setText(R.string.cancel_registration);
            button.setOnClickListener(v -> {
                // Go to cancel registration confirmation dialog
                openWarningDialog(item, dialog);
            });
        } else {
            button.setOnClickListener(v -> {
                // Go to registration dialog and close this one
                openRegisterDialog(item);
                dialog.dismiss();
            });
        }

        // Close button
        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void openRegisterDialog(VolunteerOpportunity item) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_register);
        dialog.setCancelable(false);

        // Link the XML
        TextView name = dialog.findViewById(R.id.text_name);
        TextView warn = dialog.findViewById(R.id.text_warning);
        ImageView logo = dialog.findViewById(R.id.image_logo);
        EditText fullName = dialog.findViewById(R.id.edit_name);
        EditText email = dialog.findViewById(R.id.edit_email);
        EditText phone = dialog.findViewById(R.id.edit_phone);
        EditText comments = dialog.findViewById(R.id.edit_comments);
        Button button = dialog.findViewById(R.id.button);
        Button cancel = dialog.findViewById(R.id.button_close);

        // Set known values
        name.setText(item.name);
        logo.setImageResource(item.logoDrawable);

        // Confirm button
        button.setOnClickListener(v -> {
            // Check if the required fields are all filled
            if (!fullName.getText().toString().trim().isEmpty() &&
                    !email.getText().toString().trim().isEmpty() &&
                    !phone.getText().toString().trim().isEmpty()) {
                // Go to registration confirmation dialog and close this one
                // Since we don't need to collect the user data we can just leave it here
                openConfirmationDialog(item);
                dialog.dismiss();
            } else {
                // Show the invalid input warning text
                warn.setVisibility(View.VISIBLE);
            }
        });

        // Close button goes to warning dialog
        cancel.setOnClickListener(v -> openWarningDialog(item, dialog));
        dialog.show();
    }

    public void openWarningDialog(VolunteerOpportunity item, Dialog parent) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(false);

        // Link the XML
        TextView blurb = dialog.findViewById(R.id.text_blurb);
        Button button = dialog.findViewById(R.id.button);
        Button cancel = dialog.findViewById(R.id.button_close);

        // Confirm button
        if (item.isRegistered) {
            // We can also use this dialog to confirm cancellations by editing the layout a bit
            blurb.setText(R.string.cancel_warning);
            button.setOnClickListener(v -> {
                // Run callback and close all dialogs
                clickListener.onComplete(item);
                dialog.dismiss();
                parent.dismiss();
            });
        } else {
            button.setOnClickListener(v -> {
                // Close all dialogs
                dialog.dismiss();
                parent.dismiss();
            });
        }

        // Close button
        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void openConfirmationDialog(VolunteerOpportunity item) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.setCancelable(false);

        // Link the XML
        Button button = dialog.findViewById(R.id.button);

        // Confirm button
        button.setOnClickListener(v -> {
            // Run callback and close this one
            clickListener.onComplete(item);
            dialog.dismiss();
        });
        dialog.show();
    }

    public interface ClickListener {
        void onComplete(VolunteerOpportunity item);
    }
}

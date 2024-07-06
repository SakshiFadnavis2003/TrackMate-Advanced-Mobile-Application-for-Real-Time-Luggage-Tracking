package com.example.trackmate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HowToProceed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_proceed);

        TextView textViewDoubts = findViewById(R.id.textViewDoubts);

        // Create a clickable span for "FAQs"
        ClickableSpan faqSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle click action for FAQs
                Toast.makeText(HowToProceed.this, "FAQs clicked", Toast.LENGTH_SHORT).show();
                // Example: Navigate to FAQs activity or fragment
                // Intent intent = new Intent(HowToProceed.this, FAQsActivity.class);
                // startActivity(intent);
            }
        };

        // Create a clickable span for "contact us"
        ClickableSpan contactUsSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle click action for Contact Us
                Toast.makeText(HowToProceed.this, "Contact Us clicked", Toast.LENGTH_SHORT).show();
                // Example: Open email app to contact support
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:support@example.com")); // Change email address
//                startActivity(intent);
            }
        };

        // Create a SpannableString for the doubts text
        SpannableString doubtsSpannable = new SpannableString(textViewDoubts.getText());

        // Set the clickable spans for "FAQs" and "contact us"
        doubtsSpannable.setSpan(faqSpan, 25, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        doubtsSpannable.setSpan(contactUsSpan, 33, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the modified SpannableString to the TextView
        textViewDoubts.setText(doubtsSpannable);
        textViewDoubts.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

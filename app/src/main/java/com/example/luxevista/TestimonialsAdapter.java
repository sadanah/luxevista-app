package com.example.luxevista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestimonialsAdapter extends RecyclerView.Adapter<TestimonialsAdapter.TestimonialViewHolder> {

    private List<Testimonial> testimonials;  // Changed to hold Testimonial objects

    public TestimonialsAdapter(List<Testimonial> testimonials) {
        this.testimonials = testimonials;
    }

    @NonNull
    @Override
    public TestimonialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the testimonial item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testimonials_card, parent, false);
        return new TestimonialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimonialViewHolder holder, int position) {
        // Get the current testimonial object
        Testimonial testimonial = testimonials.get(position);

        // Set the testimonial text and image
        holder.testimonialText.setText(testimonial.getText());               // Set the text
        holder.testimonialImage.setImageResource(testimonial.getImageResource());  // Set the image
    }

    @Override
    public int getItemCount() {
        return testimonials.size();
    }

    public static class TestimonialViewHolder extends RecyclerView.ViewHolder {
        TextView testimonialText;
        ImageView testimonialImage;   // Declare ImageView to hold the image

        public TestimonialViewHolder(View itemView) {
            super(itemView);
            testimonialText = itemView.findViewById(R.id.testimonialText);   // Find the TextView
            testimonialImage = itemView.findViewById(R.id.testimonialImage); // Find the ImageView
        }
    }
}


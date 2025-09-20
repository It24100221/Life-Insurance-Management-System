/**
 * Feedback.js - Handles feedback form submission to the backend API
 * Last updated: September 20, 2025
 */

document.addEventListener('DOMContentLoaded', function() {
    // Get form elements
    const feedbackForm = document.getElementById('feedbackForm');
    const modalOverlay = document.getElementById('feedbackModalOverlay');

    // Override the existing form submission if it exists
    if(feedbackForm) {
        // Replace or add submit event listener
        feedbackForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Get form data
            const name = document.getElementById('reviewerName').value;
            const rating = document.querySelector('input[name="rating"]:checked')?.value;
            const reviewText = document.getElementById('reviewText').value;

            // Validate inputs
            if(!name || !rating || !reviewText) {
                if(window.showToast) {
                    window.showToast('Please fill out all fields');
                } else {
                    alert('Please fill out all fields');
                }
                return;
            }

            // Prepare data for backend API - match exactly with your entity structure
            const feedbackData = {
                fName: name,
                rating: parseInt(rating),
                reviewText: reviewText
            };

            console.log('Submitting feedback:', feedbackData);

            try {
                // Send to backend API
                const response = await fetch('/api/feedback', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(feedbackData)
                });

                if(response.ok) {
                    // Success - get the saved feedback from response
                    const newFeedback = await response.json();
                    console.log('Feedback saved successfully:', newFeedback);

                    // Show success message
                    if(window.showToast) {
                        window.showToast('Thank you! Your feedback has been submitted.');
                    } else {
                        alert('Thank you! Your feedback has been submitted.');
                    }

                    // Close modal if function exists
                    if(window.closeModal) {
                        window.closeModal();
                    } else if(modalOverlay) {
                        modalOverlay.classList.remove('active');
                    }

                    // Reset form
                    feedbackForm.reset();

                    // Reload page after short delay to show the new feedback
                    setTimeout(() => {
                        window.location.reload();
                    }, 1500);

                } else {
                    // Error handling
                    console.error('Error submitting feedback:', await response.text());
                    if(window.showToast) {
                        window.showToast('There was an error submitting your feedback. Please try again.');
                    } else {
                        alert('There was an error submitting your feedback. Please try again.');
                    }
                }
            } catch (error) {
                console.error('Network error:', error);
                if(window.showToast) {
                    window.showToast('Network error. Please check your connection and try again.');
                } else {
                    alert('Network error. Please check your connection and try again.');
                }
            }
        });
    } else {
        console.error('Feedback form not found. Make sure the form has id="feedbackForm"');
    }
});

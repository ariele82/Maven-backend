<template>
  <AuthLayout>
    <template #left-content>
      <div class="tutorial-section">
        <div class="tutorial-header">
          <h1>SignUp TUTORIAL</h1>
          <p>UIUX SERIES</p>
        </div>
        
        <div class="tutorial-content">
          <div class="rocket-illustration">
            <!-- Placeholder per immagine rocket -->
            <div class="rocket-placeholder">
              <i class="fas fa-rocket fa-5x"></i>
            </div>
          </div>
          
          <div class="tutorial-text">
            <h2>Access to UNLIMITED Mockups and Illustrations</h2>
            <p>Join thousands of designers getting premium resources</p>
          </div>
        </div>
      </div>
    </template>
    
    <template #right-content>
      <SignupForm @submit="handleSignup" />
    </template>
  </AuthLayout>
</template>

<script>
import AuthLayout from '@/components/AuthLayout.vue'
import SignupForm from '@/components/SignupForm.vue'

export default {
  name: 'SignupView',
  components: {
    AuthLayout,
    SignupForm
  },
  methods: {
    handleSignup(formData) {
      // Gestione invio form (da implementare dopo)
      console.log('Signup data:', formData)
      // Esempio: this.$store.dispatch('auth/signup', formData)
    }
  }
}
</script>
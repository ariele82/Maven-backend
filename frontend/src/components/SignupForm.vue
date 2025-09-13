<template>
  <div class="signup-form">
    <h2 class="text-center mb-4">Create Account</h2>
    
    <b-form @submit.prevent="onSubmit">
      <b-form-group label="Full Name" label-for="fullname">
        <b-form-input
          id="fullname"
          v-model="form.fullname"
          placeholder="Enter your full name"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group label="Email Address" label-for="email">
        <b-form-input
          id="email"
          v-model="form.email"
          type="email"
          placeholder="Enter your email"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group label="Password" label-for="password">
        <b-form-input
          id="password"
          v-model="form.password"
          type="password"
          placeholder="Create password"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary" block class="mt-3">
        Sign Up
      </b-button>
    </b-form>

    <div class="text-center my-4">
      <span>or continue with</span>
    </div>

    <SocialLogin />
  </div>
</template>

<script>
import SocialLogin from './SocialLogin.vue'

export default {
  name: 'SignupForm',
  components: {
    SocialLogin
  },
  data() {
    return {
      form: {
        fullname: '',
        email: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit() {
      // Logica di invio dati (da implementare dopo)
      console.log('Form submitted:', this.form)
      // Esempio: this.$emit('submit', this.form)
    }
  }
}
</script>
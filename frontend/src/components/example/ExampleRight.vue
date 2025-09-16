<template>
  <div class="example-right">
    <div class="right-content">
      <h2>User Login</h2>
      
      <div class="login-form">
        <b-form @submit.prevent="handleLogin">
          <b-form-group label="Username" label-for="username">
            <b-form-input
              id="username"
              v-model="form.username"
              placeholder="Inserisci il tuo username"
              required
              class="dark-input"
            ></b-form-input>
          </b-form-group>
          
          <b-form-group label="Password" label-for="password">
            <b-form-input
              id="password"
              v-model="form.password"
              type="password"
              placeholder="Inserisci la tua password"
              required
              class="dark-input"
            ></b-form-input>
          </b-form-group><b-button type="submit" variant="primary" class="login-btn">
            Login
          </b-button>
        </b-form>
        
        <div class="divider">
          <span>Oppure</span>
        </div>
        
        <div class="social-login">
          <b-button variant="outline-light" class="social-btn">
            <i class="fab fa-google"></i> Google
          </b-button>
          <b-button variant="outline-light" class="social-btn">
            <i class="fab fa-facebook-f"></i> Facebook
          </b-button>
        </div>
        
        <div class="register-link">
          Non hai un account? <a href="#">Registrati</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ExampleRight',
  data() {
    return {
      form: {
        username: '',
        password: '',
        remember: false
      }
    }
  },
  methods: {
    handleLogin() {
      // Qui puoi aggiungere la logica per il login
      console.log('Login attempt:', this.form);
      alert('Login effettuato con: ' + this.form.username);
      // this.$router.push('/dashboard'); // Esempio di reindirizzamento dopo il login
    }
  }
}
</script>

<style scoped>
@font-face {
  font-family: 'Miedinger';
  src: url('@/assets/fonts/Miedinger_Bold.ttf') format('truetype');
  font-weight: bold;
  font-style: normal;
}

@font-face {
  font-family: 'Miedinger';
  src: url('@/assets/fonts/MiedingerLight.ttf') format('truetype');
  font-weight: normal;
  font-style: normal;
}
.example-right {
  background: linear-gradient(135deg, #090B10 0%, #16213e 100%);
  color: #F5F5F5;
  padding: 3rem 2rem;
  height: 100%;
  display: flex;
  align-items: center;
  font-family: 'Miedinger', sans-serif;
  font-size: 0.6rem;
}

.right-content {
  max-width: 400px;
  margin: 0 auto;
  font-size: 0.6rem;
}

.right-content h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  font-weight: 700;
  text-align: center;
  font-size: 1rem;
}

.right-content p {
  font-size: 1.1rem;
  margin-bottom: 2rem;
  opacity: 0.9;
  text-align: center;
  font-size: 0.6rem;
}

.login-form {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 15px;
  padding: 2rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  font-size: 0.6rem;
}

/* Stili per gli input scuri */
.dark-input {
  background-color: rgba(245, 245, 245, 0.1) !important;
  border: 1px solid rgba(245, 245, 245, 0.2) !important;
  color: #F5F5F5 !important;
  font-size: 0.6rem;
}

.dark-input:focus {
  background-color: rgba(245, 245, 245, 0.15) !important;
  border-color: #059BAD !important;
  box-shadow: 0 0 0 0.25rem rgba(5, 155, 173, 0.25) !important;
  color: #F5F5F5 !important;
  font-size: 0.6rem;
}

.dark-input::placeholder {
  color: rgba(245, 245, 245, 0.6) !important;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  font-size: 0.6rem;
}

.remember-me {
  color: #F5F5F5;
}

.forgot-password {
  color: #059BAD;
  text-decoration: none;
  transition: color 0.3s ease;
}

.forgot-password:hover {
  color: #04839e;
  text-decoration: underline;
}

.login-btn {
  width: 100%;
  padding: 0.75rem;
  font-weight: 600;
  border-radius: 50px;
  background-color: #059BAD !important;
  border-color: #059BAD !important;
  transition: all 0.3s ease;
  font-size: 0.6rem;
}

.login-btn:hover {
  background-color: #04839e !important;
  border-color: #04839e !important;
  transform: translateY(-2px);
}

.divider {
  text-align: center;
  margin: 1.5rem 0;
  position: relative;
}

.divider::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background-color: rgba(245, 245, 245, 0.2);
}

.divider span {
  background-color: #090B10;
  padding: 0 1rem;
  position: relative;
  z-index: 1;
}

.social-login {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  font-size: 0.6rem;
}

.social-btn {
  flex: 1;
  border-color: rgba(245, 245, 245, 0.3) !important;
  color: #F5F5F5 !important;
  transition: all 0.3s ease;
  font-size: 0.6rem;
}

.social-btn:hover {
  background-color: rgba(245, 245, 245, 0.1) !important;
  transform: translateY(-2px);
}

.register-link {
  text-align: center;
  font-size: 0.9rem;
  font-size: 0.6rem;
}

.register-link a {
  color: #059BAD;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.3s ease;
}

.register-link a:hover {
  color: #04839e;
  text-decoration: underline;
}

@media (max-width: 991.98px) {
  .example-right {
    padding: 2rem 1.5rem;
  }
  
  .right-content h2 {
    font-size: 1rem;
  }
  
  .login-form {
    padding: 1.5rem;
    font-size: 0.6rem;
  }
}

@media (max-width: 768px) {
  .social-login {
    flex-direction: column;
  }
  
  .form-options {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }
}
</style>
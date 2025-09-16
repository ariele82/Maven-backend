<template>
  <footer class="footer" :style="{ backgroundColor: '#273031' }" v-b-visible="handleVisible">
    <b-container :class="{ 'animate__animated animate__slideInUp': isVisible }">
      <!-- Contenitore per le tre immagini -->
      <div class="images-container d-flex justify-content-center align-items-center mb-4">
        <div class="image-wrapper mx-3">
          <img src="@/assets/s1.svg" alt="Service 1" class="footer-image" />
        </div>
        <div class="image-wrapper mx-3">
          <img src="@/assets/s2.svg" alt="Service 2" class="footer-image" />
        </div>
        <div class="image-wrapper mx-3">
          <img src="@/assets/s3.svg" alt="Service 3" class="footer-image" />
        </div>
      </div>
      
      <!-- Testo del copyright -->
      <div class="copyright-section text-center">
        <p class="copyright-text mb-2">&copy; {{ currentYear }} La Mia Azienda. Tutti i diritti riservati.</p>
        <div class="footer-links">
          <b-button variant="outline-light" size="sm" class="mx-2">Termini</b-button>
          <b-button variant="outline-light" size="sm" class="mx-2">Privacy</b-button>
          <b-button variant="outline-light" size="sm" class="mx-2">Contatti</b-button>
        </div>
      </div>
    </b-container>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
  data() {
    return {
      isVisible: false
    }
  },
  computed: {
    currentYear() {
      return new Date().getFullYear();
    }
  },
  methods: {
    handleVisible(isVisible) {
      this.isVisible = isVisible;
    }
  },
  mounted() {
    // Attiva l'animazione dopo un breve ritardo per garantire che il componente sia montato
    setTimeout(() => {
      this.isVisible = true;
    }, 100);
  }
}
</script>

<style scoped>
.footer {
  color: #F5F5F5;
  padding: 2rem 0;
  border-top: 1px solid rgba(245, 245, 245, 0.1);
}

/* Contenitore per le immagini */
.images-container {
  margin-bottom: 2rem;
}

/* Wrapper per ogni immagine */
.image-wrapper {
  transition: transform 0.3s ease;
}

.image-wrapper:hover {
  transform: translateY(-5px);
}

/* Stili per le immagini del footer */
.footer-image {
  width: 317px; /* Larghezza corretta */
  height: 317px; /* Altezza corretta */
}


/* Sezione del copyright */
.copyright-section {
  border-top: 1px solid rgba(245, 245, 245, 0.1);
  padding-top: 1.5rem;
}

.copyright-text {
  color: #F5F5F5;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

/* Link del footer */
.footer-links .btn {
  color: #F5F5F5;
  border-color: rgba(245, 245, 245, 0.3);
  transition: all 0.3s ease;
}

.footer-links .btn:hover {
  color: #059BAD;
  border-color: #059BAD;
  background-color: rgba(5, 155, 173, 0.1);
}

/* Animazione personalizzata per l'ingresso dal basso verso l'alto */
@keyframes slideInUp {
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.animate__slideInUp {
  animation-name: slideInUp;
  animation-duration: 1.5s; /* Durata dell'animazione */
  animation-fill-mode: both;
  animation-timing-function: ease-out; /* Movimento più naturale */
}

/* Stili per dispositivi mobili */
@media (max-width: 768px) {
  .images-container {
    flex-direction: column;
  }
  
  .image-wrapper {
    margin: 0.5rem 0;
  }
  
  .footer-image {
    width: 200px; /* Dimensioni ridotte per mobile */
    height: 200px; /* Dimensioni ridotte per mobile */
  }
  
  .footer-links {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .footer-links .btn {
    margin: 0.25rem 0;
    width: 150px;
  }
}

/* Stili per tablet */
@media (min-width: 769px) and (max-width: 1024px) {
  .footer-image {
    width: 250px; /* Dimensioni intermedie per tablet */
    height: 250px; /* Dimensioni intermedie per tablet */
  }
}
</style>
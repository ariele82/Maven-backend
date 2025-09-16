<template>
  <div class="sidebar" v-b-visible="handleVisible">
    <div class="p-4 pt-5" :class="{ 'animate__animated animate__slideInLeft': isVisible }">
      <!-- Titolo -->
      <h5 class="sidebar-title">Full Stack Web Developer<br>Designer and Backend Developing</h5>
      
      <!-- Descrizione -->
      <p class="sidebar-description">
        Web developer skilled in creating modern, responsive designs and building complete solutions across frontend and backend. Experienced with PostgreSQL, Node.js (Fastify), Java (Spring Boot), and TypeORM, delivering scalable and efficient applications.
      </p>
      
      <!-- Pulsanti -->
      <div class="d-flex mt-4">
        <!-- Pulsante Contact -->
        <b-button 
          class="contact-btn rounded px-4 me-3"
          variant="primary"
          style="background-color: #059BAD; border: none;"
        >
          Contact
        </b-button>
        
        <!-- Pulsante Try example -->
        <b-button 
          class="try-btn rounded px-4"
          variant="outline-primary"
          style="border-color: #059BAD; color: #059BAD;"
           @click="goToExample"
        >
          Try example
        </b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AppSidebar',
  data() {
    return {
     
      isVisible: false
    }
  },
  methods: {
    setActiveItem(index) {
      this.activeItem = index;
    },
    handleVisible(isVisible) {
      this.isVisible = isVisible;
    },
     goToExample() {
      this.$router.push('/example')
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

.sidebar {
  width: 700px; /* MODIFICA QUI: Larghezza della sidebar */
  min-height: calc(100vh - 56px - 56px);
  /* Effetto di sfumatura da sinistra a destra */
  background: linear-gradient(to right, #090B10 0%, #090B10 70%, rgba(9, 11, 16, 0.8) 80%, rgba(9, 11, 16, 0.5) 90%, rgba(9, 11, 16, 0) 100%);
  color: #F5F5F5; /* Testo bianco per contrasto con sfondo scuro */
  font-family: 'Miedinger', sans-serif; /* Font di base per la sidebar */
}

/* Stili per il titolo */
.sidebar-title {
  color: #F5F5F5;
  font-weight: bold; /* MODIFICA QUI: Usa il font bold per il titolo */
  line-height: 1.3;
  margin-bottom: 1rem;
  margin-top:3rem;
  font-family: 'Miedinger', sans-serif; /* Font bold per il titolo */
  font-size: 1rem; /* MODIFICA QUI: 24px (1.5rem = 24px) */
}

/* Stili per la descrizione */
.sidebar-description {
  color: #CCCCCC; /* MODIFICA QUI: Colore testo descrizione */
  font-size: 0.6rem; /* MODIFICA QUI: 16px (1rem = 16px) */
  line-height: 1.5;
  text-align: justify;
  font-family: 'Miedinger', sans-serif; /* Font light per la descrizione */
  font-weight: normal; /* MODIFICA QUI: Usa il font light per la descrizione */
}

/* Stili per i pulsanti - SENZA ANIMAZIONI */
.contact-btn, .try-btn {
  font-family: 'Miedinger', sans-serif; /* Font bold per i pulsanti */
  font-weight: bold; /* MODIFICA QUI: Usa il font bold per i pulsanti */
  font-size: 0.6rem; /* MODIFICA QUI: 16px (1rem = 16px) */
}

.contact-btn {
  background-color: #059BAD; /* MODIFICA QUI: Colore pulsante Contact */
  border: none;
}

.try-btn {
  border-color: #059BAD; /* MODIFICA QUI: Colore bordo pulsante Try example */
  color: #059BAD; /* MODIFICA QUI: Colore testo pulsante Try example */
  background-color: transparent;
}

/* Stili per i titoli delle sezioni */
.section-title {
  color: #F5F5F5;
  font-weight: normal; /* MODIFICA QUI: Usa il font light per i titoli delle sezioni */
  margin-bottom: 0.75rem;
  font-family: 'Miedinger', sans-serif;
  font-size: 1rem; /* MODIFICA QUI: 16px (1rem = 16px) */
}


/* Stili per la barra di progresso */
.progress {
  height: 8px; /* MODIFICA QUI: Altezza barra progresso */
  background-color: rgba(245, 245, 245, 0.1); /* MODIFICA QUI: Colore sfondo barra progresso */
}

.progress-text {
  color: #CCCCCC; /* MODIFICA QUI: Colore testo progresso */
  font-family: 'Miedinger', sans-serif; /* Font light per il testo progresso */
  font-weight: normal; /* MODIFICA QUI: Usa il font light per il testo progresso */
  font-size: 0.875rem; /* MODIFICA QUI: 14px (0.875rem = 14px) */
}

/* Stili per dispositivi mobili */
@media (max-width: 768px) {
  .sidebar {
    width: 100%;
    min-height: auto;
    border-right: none;
    border-bottom: 1px solid rgba(245, 245, 245, 0.1);
    /* Su mobile, manteniamo un colore di sfondo uniforme senza sfumatura */
    background: #090B10;
  }
}

/* Animazione personalizzata per l'ingresso da sinistra - PIÙ LENTA */
@keyframes slideInLeft {
  from {
    transform: translateX(-100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

.animate__slideInLeft {
  animation-name: slideInLeft;
  animation-duration: 1.5s; /* MODIFICA QUI: Durata aumentata da 0.6s a 1.5s per un'animazione più lenta */
  animation-fill-mode: both;
  animation-timing-function: ease-out; /* Aggiunto per un movimento più naturale */
}
</style>
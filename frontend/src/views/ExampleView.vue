<template>
  <div class="example-page d-flex flex-column min-vh-100">
    <AppNavbar />
    
    <div class="main-layout flex-grow-1 position-relative">
      <!-- Parte destra con contenuto a tre colonne -->
      <main class="main-content flex-grow-1 position-relative">
        <div class="three-column-layout">
          <!-- Parte sinistra -->
          <ExampleLeft class="column left-column" />
          <!-- Parte destra -->
          <ExampleRight class="column right-column" />
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import AppNavbar from '@/components/AppNavbar.vue'
import ExampleLeft from '@/components/example/ExampleLeft.vue'
import ExampleRight from '@/components/example/ExampleRight.vue'

export default {
  name: 'ExampleView',
  components: {
    AppNavbar,
    ExampleLeft,
    ExampleRight
  },
  data() {
    return {
      showCenter: true,
      windowWidth: 0
    }
  },
  methods: {
    goBack() {
      this.$router.push('/')
    },
    handleResize() {
      this.windowWidth = window.innerWidth
      // Nascondi la parte centrale su schermi più piccoli di 768px
      this.showCenter = this.windowWidth >= 768
    }
  },
  mounted() {
    // Imposta la larghezza iniziale della finestra
    this.windowWidth = window.innerWidth
    this.showCenter = this.windowWidth >= 768
    
    // Aggiungi un event listener per il ridimensionamento della finestra
    window.addEventListener('resize', this.handleResize)
  },
  beforeUnmount() {
    // Rimuovi l'event listener quando il componente viene distrutto
    window.removeEventListener('resize', this.handleResize)
  }
}
</script>

<style scoped>
.example-page {
  background-color: #1a1a1a; /* Colore di sfondo scuro */
}

.main-layout {
  position: relative;
}

.main-content {
  position: relative;
  overflow: hidden;
  min-height: calc(100vh - 56px); /* Altezza della navbar */
}

.three-column-layout {
  display: flex;
  height: 100%;
  position: relative;
}

.column {
  flex: 1;
  position: relative;
}

.left-column {
  z-index: 1;
}



.center-column {
  padding-top: 2.5rem;
  width: 100%;
  height: auto;
  max-height: 80vh; /* Altezza massima per evitare che sia troppo alto */
}

.right-column {
  z-index: 2;
}

/* Stili per schermi medi e grandi */
@media (min-width: 769px) {
  .center-column-wrapper {
    display: flex;
  }
}

/* Stili per dispositivi mobili */
@media (max-width: 768px) {
  .three-column-layout {
    flex-direction: column;
  }
  
  .center-column-wrapper {
    display: none; /* Nasconde completamente la parte centrale su schermi piccoli */
  }
  
  .left-column {
    order: 1;
    width: 100%;
  }
  
  .right-column {
    order: 2;
    width: 100%;
  }
}

/* Stili per tablet */
@media (min-width: 768px) and (max-width: 991.98px) {
  .center-column-wrapper {
    width: 80%;
  }
  
  .three-column-layout {
    flex-direction: column;
  }
  
  .center-column-wrapper {
    position: relative;
    top: 0;
    left: 0;
    transform: none;
    width: 100%;
    max-width: 100%;
    order: 2;
    margin: 2rem 0;
    height: auto;
  }
  
  .center-column {
    max-height: none;
  }
  
  .left-column {
    order: 1;
  }
  
  .right-column {
    order: 3;
  }
}

/* Stili per schermi molto piccoli */
@media (max-width: 576px) {
  .center-column-wrapper {
    width: 95%;
    padding: 0 1rem;
  }
}
</style>
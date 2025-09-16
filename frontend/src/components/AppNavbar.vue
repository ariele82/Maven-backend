<template>
   <div class="navbar-outer">
  <b-navbar :style="{ backgroundColor: '#273031' }" toggleable="lg" class="custom-navbar" :class="{ 'animate__animated animate__slideInDown': isVisible }">
    <!-- Logo come immagine a sinistra -->
    <b-navbar-brand class="logo-container">
      <img src="@/assets/logo.svg" alt="Logo" class="logo-img" />
    </b-navbar-brand>
    
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    
    <b-collapse id="nav-collapse" is-nav>
      <!-- Link di navigazione a destra -->
      <b-navbar-nav class="ms-auto">
        <b-nav-item href="#services" class="custom-nav-link">Services</b-nav-item>
        <b-nav-item href="#portfolio" class="custom-nav-link">Portfolio</b-nav-item>
        <b-nav-item href="#about" class="custom-nav-link">About</b-nav-item>
        <b-nav-item href="#contact" class="custom-nav-link">Contact</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>

<script>
export default {
  name: 'AppNavbar',
  data() {
    return {
      isVisible: false
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

<style>
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
/* Stili globali per sovrascrivere Bootstrap */
.custom-navbar .navbar-nav .nav-link {
  color: #F5F5F5 !important;
  transition: color 0.3s ease;
  padding: 0.5rem 2.5rem !important; /* Aumentato padding orizzontale a 5rem (circa 80px) */
  margin: 0 0.2rem;
  border-radius: 4px;
  position: relative;
   font-family: 'Miedinger', sans-serif; /* Font di base per la sidebar */
    font-size: 0.6rem; /* MODIFICA QUI: 16px (1rem = 16px) */
    font-weight: bold; /* MODIFICA QUI: Usa il font bold per i pulsanti */
}

.custom-navbar .navbar-nav .nav-link:hover,
.custom-navbar .navbar-nav .nav-link:focus {
  color: #059BAD !important;
  background-color: rgba(5, 155, 173, 0.1);
}
.navbar-outer {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
  position: relative;
}

.custom-navbar .navbar-nav .nav-link.active {
  color: #059BAD !important;
  font-weight: 600;
}

/* Stili personalizzati per la navbar */
.custom-navbar {
  padding: 0.5rem 1rem;
}

/* Stili per il logo */
.logo-container {
  padding: 0;
  margin-right: 1rem;
}

.logo-img {
  height: 40px;
  width: auto;
  transition: transform 0.3s ease, filter 0.3s ease;
}

.logo-container:hover .logo-img {
  transform: scale(1.05);
  filter: brightness(0) saturate(100%) invert(32%) sepia(78%) saturate(1238%) hue-rotate(160deg) brightness(96%) contrast(94%);
}

/* Effetto sottolineatura animata */
.custom-navbar .navbar-nav .nav-link::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 2px;
  background-color: #059BAD;
  transition: all 0.3s ease;
  transform: translateX(-50%);
}

.custom-navbar .navbar-nav .nav-link:hover::after {
  width: 80%;
}

/* Stili per il toggler su dispositivi mobili */
.custom-navbar .navbar-toggler {
  border-color: #F5F5F5;
  padding: 0.25rem 0.5rem;
}

.custom-navbar .navbar-toggler-icon {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 30 30'%3e%3cpath stroke='%23F5F5F5' stroke-linecap='round' stroke-miterlimit='10' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3e%3c/svg%3e");
  transition: all 0.3s ease;
}

.custom-navbar .navbar-toggler:hover .navbar-toggler-icon {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 30 30'%3e%3cpath stroke='%23059BAD' stroke-linecap='round' stroke-miterlimit='10' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3e%3c/svg%3e");
}

.custom-navbar .navbar-toggler:focus {
  box-shadow: 0 0 0 0.25rem rgba(5, 155, 173, 0.25);
}

/* Animazione personalizzata per l'ingresso dall'alto verso il basso */
@keyframes slideInDown {
  from {
    transform: translateY(-100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.animate__slideInDown {
  animation-name: slideInDown;
  animation-duration: 1s;
  animation-fill-mode: both;
  animation-timing-function: ease-out;
}

/* Stili per la versione mobile */
@media (max-width: 991.98px) {
  .custom-navbar .navbar-nav {
    text-align: center;
    padding-top: 1rem;
  }
  
  .custom-navbar .navbar-nav .nav-link {
    padding: 0.5rem 1rem !important; /* Padding ridotto per mobile */
    margin: 0.2rem 0;
  }
  
  .logo-img {
    height: 35px;
  }
}
</style>
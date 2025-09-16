<template>
  <div class="center-container" v-b-visible="handleVisible">
    <div class="animated-content" :class="{ 'animate__animated animate__slideInRight': isVisible }">
      <!-- Contenitore per l'immagine robot e il logo -->
      <div class="robot-container">
        <img src="@/assets/robot.svg" alt="Robot" class="robot-image" />
        
        <!-- Logo sovrapposto più in basso e a sinistra -->
        <div class="logo-overlaid">
          <img src="@/assets/logo2.svg" alt="Logo" class="logo-image" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CenterComponent',
  data() {
    return {
      isVisible: false
    }
  },
  methods: {
    handleVisible(isVisible) {
      this.isVisible = isVisible;
    }
  },
  mounted() {
    setTimeout(() => {
      this.isVisible = true;
    }, 100);
  }
}
</script>

<style scoped>
.center-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 2;
}

.animated-content {
  position: relative;
  height: 100%;
}

.robot-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.robot-image {
  max-width: 400px;
  height: auto;
  opacity: 0.9;
  filter: drop-shadow(0 0 10px rgba(0, 0, 0, 0.3));
}

.logo-overlaid {
  position: absolute;
  top: 60px;
  left: 280px;
}

.logo-image {
  max-width: 100px;
  height: auto;
  filter: drop-shadow(0 0 8px rgba(0, 0, 0, 0.4));
}

/* Animazione personalizzata per l'ingresso da destra verso sinistra */
@keyframes slideInRight {
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

.animate__slideInRight {
  animation-name: slideInRight;
  animation-duration: 1.5s;
  animation-fill-mode: both;
  animation-timing-function: ease-out;
}

/* Stili per dispositivi mobili */
@media (max-width: 768px) {
  .robot-image {
    max-width: 250px;
  }
  
  .logo-image {
    max-width: 70px;
  }
  
  .logo-overlaid {
    top: 80px;
    left: 50px;
  }
}
</style>
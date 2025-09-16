<template>
  <div class="d-flex flex-column min-vh-100">
    <AppNavbar  />
    
    <div class="main-layout flex-grow-1 position-relative">
      <!-- Sidebar sovrapposta -->
      <AppSidebar class="sidebar-overlaid" />
      
      <!-- Parte destra con sfondo e immagini -->
      <main class="main-content flex-grow-1 position-relative">
        <!-- Sfondo immagine (senza animazione) -->
        <div class="background-image"></div>
        
        <!-- Componente centrale con le immagini animate -->
        <CenterComponent />
      </main>
    </div>
    
    <AppFooter />
  </div>
</template>

<script>
import AppNavbar from '@/components/AppNavbar.vue'
import AppSidebar from '@/components/AppSidebar.vue'
import AppFooter from '@/components/AppFooter.vue'
import CenterComponent from '@/components/CenterComponent.vue'

export default {
  name: 'HomeView',
  components: {
    AppNavbar,
    AppSidebar,
    AppFooter,
    CenterComponent
  }
}
</script>

<style scoped>
.main-layout {
  position: relative;
}

/* Sidebar sovrapposta */
.sidebar-overlaid {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  z-index: 10;
}

.main-content {
  position: relative;
  overflow: hidden;
  min-height: calc(100vh - 56px - 56px);
  margin-left: 350px;
}

/* Sfondo immagine (visibile immediatamente senza animazione) */
.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('@/assets/bg.svg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  z-index: 1;
}

/* Stili per dispositivi mobili */
@media (max-width: 768px) {
  .sidebar-overlaid {
    position: relative;
    width: 100%;
    height: auto;
    box-shadow: none;
  }
  
  .main-content {
    margin-left: 0;
  }
}
</style>
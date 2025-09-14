
<template>
  <div class="container-fluid p-0">
    <!-- Navbar -->
    <navbar-component class="sticky-top" />
    
    <!-- Layout a 3 colonne -->
    <div class="row no-gutters">
      <!-- Colonna Sinistra -->
      <div class="col-md-3 bg-light">
        <side-sx-component />
      </div>
      
      <!-- Colonna Centrale -->
      <div class="col-md-6 bg-white">
        <main-component />
      </div>
      
      <!-- Colonna Destra -->
      <div class="col-md-3 bg-light">
        <side-dx-component />
      </div>
    </div>
  </div>
</template>

<script>
import NavbarComponent from './components/navbarComponent.vue'
import SideSxComponent from './components/sideSxComponent.vue'
import MainComponent from './components/mainComponent.vue'
import SideDxComponent from './components/sideDxComponent.vue'

export default {
  name: 'App',
  components: {
    NavbarComponent,
    SideSxComponent,
    MainComponent,
    SideDxComponent
  }
}
</script>

<style>
.navbar {
  background-color: #343a40 !important;
}
.bg-light {
  background-color: #f8f9fa !important;
  min-height: 100vh;
}
.bg-white {
  background-color: white !important;
  min-height: 100vh;
}
</style>
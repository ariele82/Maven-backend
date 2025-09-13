<template>
  <div class="text-center py-5">
    <h1>Benvenuto!</h1>
    <p class="lead">Scegli un'opzione per continuare</p>
    <div class="mt-4">
      <b-button variant="primary" to="/signup" size="lg">Registrati</b-button>
      <b-button variant="outline-primary" to="/login" size="lg" class="ms-2">Accedi</b-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>
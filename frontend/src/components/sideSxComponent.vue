<template>
  <div class="p-3">
    <h4 class="text-center mb-4">MENU SINISTRA</h4>
    <b-list-group>
      <b-list-group-item href="#" class="d-flex justify-content-between align-items-center">
        Dashboard
        <b-badge variant="primary" pill>5</b-badge>
      </b-list-group-item>
      <b-list-group-item href="#">Messaggi</b-list-group-item>
      <b-list-group-item href="#">Notifiche</b-list-group-item>
      <b-list-group-item href="#">Documenti</b-list-group-item>
      <b-list-group-item href="#">Calendario</b-list-group-item>
    </b-list-group>
    
    <div class="mt-4">
      <h5>Statistiche</h5>
      <b-progress :value="75" variant="success" class="mb-2"></b-progress>
      <b-progress :value="40" variant="warning" class="mb-2"></b-progress>
      <b-progress :value="20" variant="danger"></b-progress>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SideSxComponent'
}
</script>
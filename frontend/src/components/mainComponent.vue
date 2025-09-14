<template>
  <div class="p-4">
    <h1>CONTENUTO PRINCIPALE</h1>
    
    <b-card class="mb-4" title="Benvenuto nella tua applicazione">
      <b-card-text>
        Questo è il contenuto principale della tua applicazione. Qui puoi inserire tutte le informazioni e i componenti principali.
      </b-card-text>
      <b-button variant="primary">Scopri di più</b-button>
    </b-card>
    
    <b-row class="mb-4">
      <b-col md="6">
        <b-card title="Sezione 1">
          <b-card-text>Contenuto della prima sezione</b-card-text>
        </b-card>
      </b-col>
      <b-col md="6">
        <b-card title="Sezione 2">
          <b-card-text>Contenuto della seconda sezione</b-card-text>
        </b-card>
      </b-col>
    </b-row>
    
    <b-alert show variant="info">
      <h4>Informazioni importanti</h4>
      <p>Questo è un messaggio informativo per l'utente.</p>
    </b-alert>
  </div>
</template>

<script>
export default {
  name: 'MainComponent'
}
</script>
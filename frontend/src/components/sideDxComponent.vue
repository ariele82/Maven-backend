<template>
  <div class="p-3">
    <h4 class="text-center mb-4">PANNELLO DESTRO</h4>
    
    <b-card title="Utente" class="mb-3">
      <b-card-text>
        <div class="d-flex align-items-center mb-3">
          <b-avatar variant="info" src="https://placekitten.com/200/200" class="mr-3"></b-avatar>
          <div>
            <h6 class="mb-0">Mario Rossi</h6>
            <small>mario.rossi@example.com</small>
          </div>
        </div>
        <b-button variant="outline-primary" block>Profilo</b-button>
      </b-card-text>
    </b-card>
    
    <b-card title="Attività Recenti" class="mb-3">
      <b-list-group flush>
        <b-list-group-item>
          <h6 class="mb-1">Nuovo messaggio</h6>
          <small>Hai ricevuto un nuovo messaggio da Anna</small>
        </b-list-group-item>
        <b-list-group-item>
          <h6 class="mb-1">Aggiornamento completato</h6>
          <small>Il sistema è stato aggiornato con successo</small>
        </b-list-group-item>
        <b-list-group-item>
          <h6 class="mb-1">Promemoria</h6>
          <small>Riunione alle 15:00</small>
        </b-list-group-item>
      </b-list-group>
    </b-card>
    
    <b-card title="Impostazioni Rapide">
      <b-form-checkbox v-model="notifications" name="check-button" switch>
        Notifiche
      </b-form-checkbox>
      <b-form-checkbox v-model="darkMode" name="check-button" switch>
        Modalità Scura
      </b-form-checkbox>
    </b-card>
  </div>
</template>

<script>
export default {
  name: 'SideDxComponent',
  data() {
    return {
      notifications: true,
      darkMode: false
    }
  }
}
</script>
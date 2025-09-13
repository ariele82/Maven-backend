<template>
  <div class="auth-container">
    <div class="auth-left">
      <slot name="left-content"></slot>
    </div>
    <div class="auth-right">
      <slot name="right-content"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AuthLayout'
}
</script>

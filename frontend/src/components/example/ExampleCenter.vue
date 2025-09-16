<template>
  <div class="example-center">
    <div class="center-content">
      <div class="smartphone-container">
        <div class="smartphone-frame">
          <div class="smartphone-notch"></div>
          <div class="smartphone-screen">
            <video 
              class="smartphone-video" 
              autoplay 
              muted 
              loop 
              playsinline
            >
              <source src="@/assets/video/video.mp4" type="video/mp4">
              Il tuo browser non supporta il tag video.
            </video>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ExampleCenter'
}
</script>

<style scoped>
.example-center {
  position: relative;
  z-index: 10;
  padding: 3rem 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100%;
}

.center-content {
  width: 100%;
  max-width: 300px; /* Ridotto da 400px a 300px per renderlo più alto */
}

.smartphone-container {
  position: relative;
  width: 100%;
  padding-bottom: 180%; /* Ridotto da 200% a 180% per renderlo più alto */
  height: 0;
}

.smartphone-frame {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%);
  border-radius: 40px;
  box-shadow: 
    0 20px 60px rgba(0, 0, 0, 0.3),
    inset 0 0 0 2px rgba(255, 255, 255, 0.1);
  padding: 15px;
  box-sizing: border-box;
}

.smartphone-notch {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 120px; /* Ridotto da 150px a 120px */
  height: 20px; /* Ridotto da 25px a 20px */
  background: #1a1a1a;
  border-radius: 0 0 15px 15px; /* Ridotto da 20px a 15px */
  z-index: 2;
}

.smartphone-notch::before {
  content: '';
  position: absolute;
  top: 8px; /* Ridotto da 10px a 8px */
  left: 50%;
  transform: translateX(-50%);
  width: 50px; /* Ridotto da 60px a 50px */
  height: 4px; /* Ridotto da 5px a 4px */
  background: #333;
  border-radius: 8px; /* Ridotto da 10px a 8px */
}

.smartphone-screen {
  position: absolute;
  top: 20px; /* Ridotto da 25px a 20px */
  left: 15px;
  right: 15px;
  bottom: 15px;
  background: #000;
  border-radius: 25px;
  overflow: hidden;
}

.smartphone-video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 20px;
}

@media (max-width: 991.98px) {
  .example-center {
    padding: 2rem 1rem;
  }
  
  .center-content {
    max-width: 250px; /* Smartphone più piccolo su tablet */
  }
  
  .smartphone-container {
    padding-bottom: 160%; /* Proporzioni più alte per tablet */
  }
}

@media (max-width: 768px) {
  .center-content {
    max-width: 200px; /* Smartphone ancora più piccolo su mobile */
  }
  
  .smartphone-container {
    padding-bottom: 140%; /* Proporzioni ancora più alte per mobile */
  }
  
  .smartphone-notch {
    width: 100px; /* Notch più piccolo su mobile */
    height: 15px;
    border-radius: 0 0 10px 10px;
  }
  
  .smartphone-notch::before {
    top: 6px;
    width: 40px;
    height: 3px;
    border-radius: 6px;
  }
}
</style>
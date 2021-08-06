<template>
    <div class="curate-thumbnail" tabindex="-1" :data-key="dataKey">
        <img class="curation-thumbnail__image" :src="imageUrl" alt="picture" />
    </div>
</template>

<script>

export default {
    name: 'ImageThumbnail',
    props: {
      imageUrl: {
        type: String
      },
      dataKey: {
          type: Number
      }
    },
}
</script>

<style scoped>
.curate-thumbnail {
  display: block;
  max-width: 84px;
  max-height: 84px;
  margin-left: 0.5rem;
  padding: 0.5rem 0;
  cursor: pointer;
}

.curation-thumbnail__image {
  width: auto;
  object-fit: cover;
  height: 84px;
  width: 84px;
}

.curate-thumbnail > img {
  filter: grayscale(100%);
  transition-duration: 0.35s;
}


.curate-thumbnail:active > img {
  transform: scale(0.9);
  transition-duration: 100ms;
}

.curate-thumbnail:focus {
  outline: none;
}

.curate-thumbnail[data-active] > img, .curate-thumbnail:hover > img {
  filter: grayscale(0%);
}

</style>
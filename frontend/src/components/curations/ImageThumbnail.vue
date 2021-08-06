<template>
    <div class="ui-thumbnail" tabindex="-1" :data-key="dataKey">
        <img class="curation-thumbnail__image" :src="imageUrl" alt="picture" />
        <div class="ui-cuticle" data-flip-key="cuticle"></div>
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
.ui-thumbnail {
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
</style>
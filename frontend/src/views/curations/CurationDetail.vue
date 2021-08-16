<template>
    <div id="app" data-state="0">
        <div class="curate-big-images">
            <ImageMain
            v-for="(image, idx) in curationImages"
            :key="1-idx"
            :imageUrl="url+image.postingImg"
            :dataKey="idx"
            :postingId="image.postingId"
            />
        </div>
        <div class="curate-thumbnails">
            <ImageThumbnail
            v-for="(image, idx) in curationImages"
            :key="2-idx"
            :imageUrl="url+image.postingImg"
            :dataKey="idx"
            />
        </div>
        <div class="curate-content">
            <nav class="curate-nav">
                <button id="prev" tabindex="-1" title="Previous">&lt;</button>
                <button id="next" tabindex="-1" title="Next">&gt;</button>
            </nav>
            <div class="curate-articles">
                <ImageArticle
                v-for="(image, idx) in curationImages"
                :key="3-idx"
                :title="image.title"
                :description="image.content"
                :dataKey="idx"
                />
            </div>
        </div>
    </div>
</template>

<script>
import ImageMain from '@/components/curations/ImageMain'
import ImageThumbnail from '@/components/curations/ImageThumbnail'
import ImageArticle from '@/components/curations/ImageArticle'
import { mapState } from 'vuex'
import axios from "axios"

export default {
    name: "CurationPage",
    components: {
        ImageMain,
        ImageThumbnail,
        ImageArticle
    },
    data:  function () {
        return {
            page: 0,
            url: "https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/",
            curationImages: []
        }
    },
    methods: {
        // 숫자를 컨트롤 한다.
        send: function(event) {
            // 이 함수의 제일 밑에서 추가해준다.
            const activeElements = document.querySelectorAll('[data-active]');
            // 큰 화면
            const mainImages = Array.from(document.querySelectorAll('.curate-big-image'));

            Array.from(activeElements)
                .forEach(el => el.removeAttribute('data-active'));
            
            // 왼쪽 오른쪽 버튼은 하나씩 더하고 빼고, 그림 클릭은 그 숫자 페이지로 바로
            if (event === 'PREV') {
                this.page -= 1
            } else if (event === 'NEXT') {
                this.page += 1
            } else {
                this.page = event
            }
            var len = mainImages.length;
            this.page = Math.max(0, Math.min( this.page, len - 1) );
            // 3개가 선택된다. (사진, 썸네일, 설명)
            Array.from(document.querySelectorAll(`[data-key="${this.page}"]`))
                .forEach( el => {
                    el.setAttribute('data-active', true);
            });
        }

    },
    computed: {
      ...mapState([ 'host'])
    },
    created() {
        axios.get(`${this.host}/curations/detail/${this.$route.params.id}`)
            .then(res => {
                console.log(res)
                this.curationImages = res.data.postings
                if (res.data.color) {
                    const vMain = document.querySelector(".v-main")
                    const curateThumbnails = document.querySelector(".curate-thumbnails")
                    vMain.style.backgroundColor = '#' + res.data.color
                    curateThumbnails.style.backgroundColor = '#' + res.data.color
                }
            })
            .catch(err => {
                console.log(err)
            })
    },
    updated() {
        // 버튼 혹은 썸네일을 통해서 동작한다.
        const prevButton = document.querySelector('#prev');
        const nextButton = document.querySelector('#next');
        const thumbnails = Array.from(document.querySelectorAll('.curate-thumbnail'));

        // 클릭했을 때 어느 걸 클릭했는지 알린다.
        thumbnails.forEach( thumb => { 
            thumb.addEventListener('click', () => {
                this.send(thumb.dataset.key);
            });
        });

        // 이전
        prevButton.addEventListener('click', () => {
            this.send('PREV');
        });

        // 이후
        nextButton.addEventListener('click', () => {
            this.send('NEXT');
        });
        // 첫 이미지 보여주기
        this.send(0);
    },
    destroyed () {
        const vMain = document.querySelector(".v-main")
        vMain.style.backgroundColor = ''
    }
}
</script>

<style scoped>
/* 여기에만 적용되야 해서 scss 파일을 따로 안 만들고 scoped로 css로 적겠다. */
*, *:before, *:after {
  box-sizing: border-box;
  transition-property: transform, opacity, filter;
  transition-duration: 0.7s, 0.7s, 0.7s;
  transition-timing-function: cubic-bezier(0.5, 0, 0.5, 1);
}

.curate-big-images {
  position: absolute;
  top: -4%;
  height: 100%;
  width: 100%;
  /* overflow: hidden; */
  display: flex;
}

.curate-thumbnails {
  position: absolute;
  width: 100%;
  bottom: 15px;
  left: 200px;
  background-color: #fff;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  padding: 0 1rem;
  padding-right: 45%;
  padding-right: calc( 45% + .5rem );
}

.curate-content {
  position: absolute;
  width: 30%;
  right: 10%;
  bottom: 0;
}

.curate-articles {
  background: #493e56;
  color: white;
  display: flex;
  align-items: stretch;
  overflow: hidden;
  height: 100px;
}

.curate-nav {
  position: absolute;
  right: 0;
  bottom: 60%;
  background: inherit;
  z-index: 1;
}

.curate-nav button {
  background: #493e56;
  border: none;
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  padding: 0.5em;
  width: 2em;
  color: #FFF;
  font-family: monospace;
  transition: inherit;
  transition-duration: 300ms;
}

.curate-nav button:hover, .curate-nav button:focus {
  background: #D4A12D;
  outline: none;
}

.curate-nav button:active {
  outline: none;
  transform: translateY(0.25em);
  transition-duration: 100ms;
}

</style>
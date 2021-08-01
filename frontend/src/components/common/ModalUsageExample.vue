<template>
  <div>
    <h1>Test</h1>
    <!-- emit할 event 이름은 {msg.name}-ok-sign으로 (여러개의 모달을 사용한다면) 모달마다 각각 설정해줘야 한다. -->
    <Modal
      :msg="msg"
      @modalExample-ok-sign="myFunction"
    />
  </div>
</template>

<script>
import Modal from '@/components/common/Modal'

export default {
  name: 'ModalUsageExample',
  components: {
    Modal,
  },
  data: function () {
    return {
      // 필요한 것만 입력하고 나머지는 빈칸('')으로 둬도 된다
      msg: {
        name: 'modalExample',
        triggerBtn: '버튼',
        title: '타이틀',
        text: '내용<br/>줄바꿈 가능',
        positiveBtn: '확인',
        negativeBtn: '취소',
      },
    }
  },
  methods: {
    // modal 창에서 positiveBtn을 클릭했을 때 실행할 함수
    myFunction: function () {
      console.log('이후 진행할 함수 실행')
    },
  }
}
</script>

<style>

</style>

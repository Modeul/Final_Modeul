import App from "./App.vue";
import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPersist from "pinia-plugin-persist";
import { createRouter, createWebHashHistory } from "vue-router";
import vue3GoogleLogin from "vue3-google-login";
import { decodeCredential } from "vue3-google-login";

import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

import "@mdi/font/css/materialdesignicons.css";

import { useUserDetailsStore } from "./stores/useUserDetailsStore.js";
import { useDefaultStore } from "./stores/useDefaultStore.js";

const vuetify = createVuetify({
  components,
  directives
  // theme: { defaultTheme: 'light' },
});

import Layout from "./components/Layout.vue";
import Index from "./components/Index.vue";
import Signup from "./components/Signup.vue";
import GoogleSignup from "./components/GoogleSignup.vue";
import Login from "./components/Login.vue";
import Chat from "./components/Chat.vue";
import FindPwd from "./components/FindPwd.vue";
import FindId from "./components/FindId.vue";

import MyPage from "./components/member/MyPage.vue";
import MypageEdit from "./components/member/MypageEdit.vue";
import MyInfoEdit from "./components/member/MyInfoEdit.vue";
import Favorite from "./components/member/Favorite.vue";

import MemberLayout from "./components/member/Layout.vue"; // 그냥 Layout이라고 또 쓸 수도 있다?
import List from "./components/member/stuff/List.vue";
import MyRegList from "./components/member/stuff/MyRegList.vue";
import MyDutchList from "./components/member/stuff/MyDutchList.vue";
import Detail from "./components/member/stuff/Detail.vue";
import Reg from "./components/member/stuff/Reg.vue";
import EditReg from "./components/member/stuff/EditReg.vue";
import ListSearch from "./components/member/stuff/ListSearch.vue";
import CrawlingList from "./components/member/stuff/CrawlingList.vue";
import CrawlingReg from "./components/member/stuff/CrawlingReg.vue";
import Gps from "./components/member/stuff/Gps.vue";
import ParticipationList from "./components/member/participation/List.vue";

import AdminLayout from "./components/admin/Layout.vue";
import AdminLogin from "./components/admin/Login.vue";
import AdminIndex from "./components/admin/Index.vue";

import MemberList from "./components/admin/member/List.vue";
import StuffList from "./components/admin/stuff/List.vue";
import CategoryList from "./components/admin/category/List.vue";
import Analytics from "./components/admin/analytics/List.vue";
import ReportList from "./components/admin/report/List.vue";

const routes = [
  {
    path: "/",
    component: Layout,
    children: [
      { path: "", component: Index },
      { path: "login", component: Login },
      { path: "login/findpwd", component: FindPwd },
      { path: "login/findid", component: FindId },
      { path: "signup", component: Signup },
      { path: "GoogleSignup", component: GoogleSignup }
    ]
  },
  {
    path: "/chat",
    children: [
      {
        path: ":stuffId",
        component: Chat,
        /** 로그인한 아이디가 stuffId에 참여한 경우만 채팅 참가 가능 */
        async beforeEnter(to, from, next) {
          let userDetails = useUserDetailsStore();
          let defaultStore = useDefaultStore();
          const stuffId = to.path.split("/")[2];
          if (!userDetails.isAuthenticated) next("/login");
          else {
            const response = await fetch(
              `${defaultStore.host}/api/participation/stuff/${stuffId}`
            );
            const result = await response.json();
            const participantList = result.list.map((item) => item.memberId);
            if (participantList.indexOf(userDetails.id) == -1)
              next(`/member/stuff/${stuffId}`); // -> 접근불가페이지로!
            next();
          }
        }
      }
    ]
  },
  {
    path: "/member",
    component: MemberLayout,
    children: [
      {
        path: "mypage",
        children: [
          { path: "", component: MyPage },
          { path: "edit", component: MypageEdit },
          { path: "myInfoEdit", component: MyInfoEdit },
          { path: "favorite", component: Favorite },
          { path: "myreglist", component: MyRegList },
          { path: "mydutchlist", component: MyDutchList }
        ]
      },
      {
        path: "stuff",
        children: [
          { path: "list", component: List },
          { path: ":id", component: Detail },
          { path: "reg", component: Reg },
          { path: "edit/:id", component: EditReg },
          { path: "listsearch", component: ListSearch },
          { path: "recommends", component: CrawlingList },
          { path: "recommend/:id", component: CrawlingReg },
          { path: "gps", component: Gps }
        ]
      },
      { path: "participation/list", component: ParticipationList }
    ],
    beforeEnter(to, from, next) {
      let userDetails = useUserDetailsStore();

      if (!userDetails.isAuthenticated) next("/login");
      else next();
    }
  },
  { path: "/admin/login", component: AdminLogin },
  {
    path: "/admin",
    component: AdminLayout,
    children: [
      { path: "index", component: AdminIndex },
      { path: "member/list", component: MemberList },
      { path: "stuff/list", component: StuffList },
      { path: "category/list", component: CategoryList },
      { path: "report/list", component: ReportList }
    ],
    beforeEnter(to, from, next) {
      let userDetails = useUserDetailsStore();

      let url = `/login?returnURL=${to.path}`;

      if (!userDetails.isAuthenticated) next(url);
      else if (userDetails.hasRole("ADMIN")) next();
      else next("/error/403");
    }
  }
];

const router = createRouter({
  // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
  // router 기록!
  history: createWebHashHistory(),
  routes // short for `routes: routes`
});

const pinia = createPinia();
pinia.use(piniaPersist);

// 이제는 .js파일이 아니라 뷰엔진(변환기!!)이 들어간 .vue 파일을 이용한다.
createApp(App)
  .use(router)
  .use(vuetify)
  .use(pinia)
  .use(vue3GoogleLogin, {
    clientId:
      "795034312186-55p1uq4gccthiuc9raeqt51ph1uk1qri.apps.googleusercontent.com"
  })
  .mount("#app");

import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Cookies from "js-cookie";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/singup',
    name: 'singup',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/singup.vue')
  },
  {
    path: "/newtravel",
    name: 'newtravel',
    component: () => import('../views/newtravel.vue')
  },
  {
    path: '/member',
    name: 'Member',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/Member.vue'),
    beforeEnter(to, from, next) {
      if (Cookies.get("islogin")!=null) {
        next();
      }
      else{ 
        next({ name: 'About' });
      }
    }
  },
 
  {
    path: '/back',
    name: 'back',
    component: () => import('../views/back.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,

})

export default router

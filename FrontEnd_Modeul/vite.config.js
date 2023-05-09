import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [vue()],
	resolve: {
		alias: {
			'@': fileURLToPath(new URL('./src', import.meta.url))
		}
	},
	// build: {
		// emptyOutDir: true,
		// outDir: "../BackEnd_Modeul/src/main/resources/static",
	// },
	// server: {
  //   proxy: {
  //     "/api": "http://localhost:8080",
  //   },
  // },
 	//  server: {
	// 	hmr: {
	// 	overlay: false,
	// 	},
	// },
	// devServer: {
	// 	proxy: 'http://localhost:8080/api'
	// }
})

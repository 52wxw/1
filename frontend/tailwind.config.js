/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/**/*.{vue,js,ts,jsx,tsx}',
    './node_modules/element-plus/dist/index.esm.js'
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: '#7C3AED', // 主色调：紫色
        secondary: '#10B981', // 辅助色：绿色
        accent: '#F59E0B', // 强调色：橙色

        // 深色模式
        'dark-bg': '#0F172A',
        'dark-card': '#1E293B',
        'dark-border': '#334155',
        'dark-300': '#334155',

        // 状态色
        success: '#10B981',
        warning: '#F59E0B',
        error: '#EF4444',
        info: '#3B82F6',

        // 中性色
        'neutral-100': '#F3F4F6',
        'neutral-200': '#E5E7EB',
        'neutral-300': '#D1D5DB',
        'neutral-400': '#9CA3AF',
        'neutral-500': '#6B7280',
        'neutral-600': '#4B5563',
        'neutral-700': '#374151',
        'neutral-800': '#1F2937',
        'neutral-900': '#111827',

        // 任务分类颜色（背景和文本）
        'web-bg': '#7C3AED',
        'web-text': '#7C3AED',
        'crypto-bg': '#10B981',
        'crypto-text': '#10B981',
        'pwn-bg': '#EF4444',
        'pwn-text': '#EF4444',
        'reversing-bg': '#F59E0B',
        'reversing-text': '#F59E0B',
        'misc-bg': '#6B7280',
        'misc-text': '#6B7280',
        'forensics-bg': '#3B82F6',
        'forensics-text': '#3B82F6',
        'osint-bg': '#45a868',
        'osint-text': '#45a868',
        'hardware-bg': '#2563eb',
        'hardware-text': '#2563eb',

        // 难度标签颜色
        'easy-bg': '#10B981',
        'easy-text': '#10B981',
        'medium-bg': '#F59E0B',
        'medium-text': '#F59E0B',
        'hard-bg': '#EF4444',
        'hard-text': '#EF4444',
      },
      fontFamily: {
        inter: ['Inter', 'sans-serif'],
      },
      boxShadow: {
        'card': '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
        'card-hover': '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
        'inner-lg': 'inset 0 2px 4px 0 rgba(0, 0, 0, 0.06)'
      },
      animation: {
        'fade-in': 'fadeIn 0.3s ease-in-out',
        'slide-up': 'slideUp 0.3s ease-out',
        'pulse-slow': 'pulse 3s cubic-bezier(0.4, 0, 0.6, 1) infinite'
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' }
        },
        slideUp: {
          '0%': { transform: 'translateY(10px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' }
        }
      }
    }
  },
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography'),
    // 移除 @tailwindcss/line-clamp（Tailwind 3.3+ 已内置）
    require('@tailwindcss/aspect-ratio'),
    function({ addVariant }) {
      addVariant('child', '& > *');
      addVariant('child-hover', '& > *:hover');
    }
  ]
};

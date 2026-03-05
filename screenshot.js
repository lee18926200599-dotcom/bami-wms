const puppeteer = require('puppeteer');
const fs = require('fs');
const path = require('path');

(async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  
  // 设置视口大小
  await page.setViewport({ width: 1280, height: 720 });
  
  // 读取HTML文件
  const htmlPath = path.join(require('os').homedir(), 'Desktop', 'ppt_slides', 'preview.html');
  await page.goto('file://' + htmlPath);
  
  // 等待页面加载
  await page.waitForTimeout(1000);
  
  // 截取第1页
  const outputDir = path.join(require('os').homedir(), 'Desktop', 'ppt_slides');
  
  await page.screenshot({
    path: path.join(outputDir, 'slide_01.png'),
    clip: { x: 0, y: 0, width: 1280, height: 720 }
  });
  
  console.log('Slide 1 screenshot saved');
  
  await browser.close();
})();
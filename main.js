const canvas = document.getElementById("game");
const context = canvas.getContext("2d");
const scoreEl = document.getElementById("score");
const livesEl = document.getElementById("lives");

const world = {
  width: canvas.width,
  height: canvas.height,
  gravity: 0.8,
  friction: 0.75,
};

const player = {
  position: { x: 120, y: 200 },
  velocity: { x: 0, y: 0 },
  width: 36,
  height: 48,
  speed: 4,
  jumpStrength: 14,
  color: "#38bdf8",
  onGround: false,
  lives: 3,
};

const checkpoints = [{ x: 120, y: 200 }];
let score = 0;

const platforms = [
  { x: 0, y: 480, width: 960, height: 60 },
  { x: 120, y: 380, width: 140, height: 20 },
  { x: 360, y: 320, width: 160, height: 20 },
  { x: 620, y: 260, width: 180, height: 20 },
  { x: 820, y: 200, width: 100, height: 20 },
];

const hazards = [
  { x: 280, y: 450, width: 60, height: 30 },
  { x: 520, y: 450, width: 60, height: 30 },
];

const crystals = [
  { x: 150, y: 330, radius: 10, collected: false },
  { x: 420, y: 270, radius: 10, collected: false },
  { x: 700, y: 210, radius: 10, collected: false },
  { x: 860, y: 150, radius: 10, collected: false },
];

const keys = new Set();

function updateHud() {
  scoreEl.textContent = score;
  livesEl.textContent = player.lives;
}

function resetPlayer() {
  const checkpoint = checkpoints[checkpoints.length - 1];
  player.position.x = checkpoint.x;
  player.position.y = checkpoint.y;
  player.velocity.x = 0;
  player.velocity.y = 0;
}

function applyPhysics() {
  player.velocity.y += world.gravity;
  player.position.x += player.velocity.x;
  player.position.y += player.velocity.y;

  player.velocity.x *= world.friction;

  if (player.position.x < 0) {
    player.position.x = 0;
  }
  if (player.position.x + player.width > world.width) {
    player.position.x = world.width - player.width;
  }
  if (player.position.y + player.height > world.height) {
    player.position.y = world.height - player.height;
    player.velocity.y = 0;
    player.onGround = true;
  }
}

function detectPlatformCollision(platform) {
  const playerBottom = player.position.y + player.height;
  const platformTop = platform.y;
  const playerPrevBottom = playerBottom - player.velocity.y;
  const withinX =
    player.position.x + player.width > platform.x &&
    player.position.x < platform.x + platform.width;

  if (
    withinX &&
    playerBottom >= platformTop &&
    playerPrevBottom <= platformTop
  ) {
    player.position.y = platformTop - player.height;
    player.velocity.y = 0;
    player.onGround = true;
  }
}

function detectHazards() {
  return hazards.some((hazard) => {
    const intersects =
      player.position.x < hazard.x + hazard.width &&
      player.position.x + player.width > hazard.x &&
      player.position.y < hazard.y + hazard.height &&
      player.position.y + player.height > hazard.y;
    return intersects;
  });
}

function detectCrystals() {
  crystals.forEach((crystal) => {
    if (crystal.collected) return;
    const dx = player.position.x + player.width / 2 - crystal.x;
    const dy = player.position.y + player.height / 2 - crystal.y;
    if (Math.hypot(dx, dy) < crystal.radius + 16) {
      crystal.collected = true;
      score += 1;
      updateHud();
    }
  });
}

function handleInput() {
  if (keys.has("ArrowLeft") || keys.has("KeyA")) {
    player.velocity.x = -player.speed;
  }
  if (keys.has("ArrowRight") || keys.has("KeyD")) {
    player.velocity.x = player.speed;
  }
  if (
    (keys.has("ArrowUp") || keys.has("KeyW") || keys.has("Space")) &&
    player.onGround
  ) {
    player.velocity.y = -player.jumpStrength;
    player.onGround = false;
  }
}

function drawBackground() {
  const gradient = context.createLinearGradient(0, 0, 0, world.height);
  gradient.addColorStop(0, "#1e293b");
  gradient.addColorStop(1, "#0f172a");
  context.fillStyle = gradient;
  context.fillRect(0, 0, world.width, world.height);
}

function drawPlatforms() {
  context.fillStyle = "#334155";
  platforms.forEach((platform) => {
    context.fillRect(platform.x, platform.y, platform.width, platform.height);
  });
}

function drawHazards() {
  context.fillStyle = "#ef4444";
  hazards.forEach((hazard) => {
    context.fillRect(hazard.x, hazard.y, hazard.width, hazard.height);
  });
}

function drawCrystals() {
  crystals.forEach((crystal) => {
    if (crystal.collected) return;
    context.beginPath();
    context.fillStyle = "#facc15";
    context.arc(crystal.x, crystal.y, crystal.radius, 0, Math.PI * 2);
    context.fill();
  });
}

function drawPlayer() {
  context.fillStyle = player.color;
  context.fillRect(
    player.position.x,
    player.position.y,
    player.width,
    player.height
  );
}

function drawUiMessage() {
  if (score < crystals.length) return;
  context.fillStyle = "rgba(15, 23, 42, 0.8)";
  context.fillRect(260, 40, 440, 60);
  context.fillStyle = "#f8fafc";
  context.font = "20px sans-serif";
  context.fillText("Рівень пройдено! Усі кристали зібрано.", 290, 78);
}

function step() {
  player.onGround = false;
  handleInput();
  applyPhysics();
  platforms.forEach(detectPlatformCollision);
  detectCrystals();

  if (detectHazards() || player.position.y > world.height + 100) {
    player.lives -= 1;
    updateHud();
    if (player.lives <= 0) {
      player.lives = 3;
      score = 0;
      crystals.forEach((crystal) => {
        crystal.collected = false;
      });
      updateHud();
    }
    resetPlayer();
  }

  drawBackground();
  drawPlatforms();
  drawHazards();
  drawCrystals();
  drawPlayer();
  drawUiMessage();

  requestAnimationFrame(step);
}

document.addEventListener("keydown", (event) => {
  keys.add(event.code);
});

document.addEventListener("keyup", (event) => {
  keys.delete(event.code);
});

updateHud();
resetPlayer();
step();

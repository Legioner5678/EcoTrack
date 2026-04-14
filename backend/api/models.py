from django.db import models
from django.contrib.auth.models import User

# 1. Профиль пользователя (очки и ранг)
class UserProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    total_points = models.IntegerField(default=0)
    eco_rank = models.CharField(max_length=100, default="Guardian")
    
    def __str__(self):
        return self.user.username

# 2. Привычки (для экрана Habits)
class Habit(models.Model):
    title = models.CharField(max_length=255)
    description = models.TextField()
    points_reward = models.IntegerField(default=10)
    goal_target = models.IntegerField(default=1) # например, 3 раза в день

    def __str__(self):
        return self.title

# 3. Лог выполненных привычек
class HabitLog(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    habit = models.ForeignKey(Habit, on_delete=models.CASCADE)
    date_completed = models.DateTimeField(auto_now_add=True)

# Модель EcoPoint для карты
class EcoPoint(models.Model):
    name = models.CharField(max_length=255)
    address = models.CharField(max_length=500)
    latitude = models.FloatField()
    longitude = models.FloatField()
    description = models.TextField(blank=True)


from django.contrib import admin
from .models import EcoPoint, Habit, UserProfile, HabitLog

admin.site.register(EcoPoint)
admin.site.register(Habit)
admin.site.register(UserProfile)
admin.site.register(HabitLog)
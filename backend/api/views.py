from rest_framework import generics, permissions, status
from rest_framework.response import Response
from django.contrib.auth.models import User
from .models import Habit, EcoPoint, UserProfile
from .serializers import EcoPointSerializer, HabitSerializer, UserProfileSerializer, UserSerializer

class EcoPointList(generics.ListAPIView):
    queryset = EcoPoint.objects.all()
    serializer_class = EcoPointSerializer

class HabitList(generics.ListAPIView):
    queryset = Habit.objects.all()
    serializer_class = HabitSerializer

class UserProfileView(generics.RetrieveAPIView):
    serializer_class = UserProfileSerializer
    permission_classes = [permissions.IsAuthenticated]

    def get_object(self):
        profile, created = UserProfile.objects.get_or_create(user=self.request.user)
        return profile

class RegisterView(generics.CreateAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
    permission_classes = [permissions.AllowAny]
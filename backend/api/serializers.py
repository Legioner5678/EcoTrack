from rest_framework import serializers
from .models import Habit, EcoPoint, UserProfile
from django.contrib.auth.models import User
from django.core.mail import send_mail
from django.conf import settings

class EcoPointSerializer(serializers.ModelSerializer):
    class Meta:
        model = EcoPoint
        fields = '__all__'

class HabitSerializer(serializers.ModelSerializer):
    name = serializers.CharField(source='title')
    points = serializers.IntegerField(source='points_reward')
    class Meta:
        model = Habit
        fields = ['id', 'name', 'description', 'points', 'goal_target']

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'email', 'password']
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        user = User.objects.create_user(**validated_data)
        try:
            subject = 'Welcome to EcoTrack!'
            message = f'Hi {user.username},\n\nThank you for joining the EcoTrack mission!'
            email_from = settings.EMAIL_HOST_USER
            recipient_list = [user.email]
            send_mail(subject, message, email_from, recipient_list)
        except Exception as e:
            print(f"Email failed to send: {e}")
        return user

class UserProfileSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username', read_only=True)
    class Meta:
        model = UserProfile
        fields = ['username', 'total_points', 'eco_rank']
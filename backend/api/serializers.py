from rest_framework import serializers
from .models import EcoPoint

class EcoPointSerializer(serializers.ModelSerializer):
    class Meta:
        model = EcoPoint
        fields = '__all__'
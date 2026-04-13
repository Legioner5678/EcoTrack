from rest_framework import generics
from .models import EcoPoint
from .serializers import EcoPointSerializer

class EcoPointList(generics.ListCreateAPIView):
    queryset = EcoPoint.objects.all()
    serializer_class = EcoPointSerializer
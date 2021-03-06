# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-08-13 17:02
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('polls', '0009_auto_20170813_1659'),
    ]

    operations = [
        migrations.AlterField(
            model_name='userinfo',
            name='userInfo_feedback',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to='polls.Feedback'),
        ),
        migrations.AlterField(
            model_name='userinfo',
            name='userInfo_study',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='polls.Study'),
        ),
    ]

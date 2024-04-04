#include <gsl/gsl_fft_complex.h>
#include <iostream>
#include <cstring>
#include <cmath>
#include <jni.h>
#include "dev_sentchart_math_NativeMath.h"

#define REAL(z,i) ((z)[2*(i)])
#define IMAG(z,i) ((z)[2*(i)+1])

bool isPowerOfTwo(long int n) {
    return (n & (n - 1)) == 0;
}

long int nextPowerOfTwo(long int n) {
    if (isPowerOfTwo(n)) {
        return n;
    } else {
        long int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }
}

void hanning_window(double* signal, long int n) {
    double pi = acos(-1);
    for (long int i = 0; i < n; ++i) {
        double multiplier = 0.5 * (1 - std::cos(2 * pi * (double)i / (double)(n - 1)));
        signal[i] *= multiplier;
    }
}

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#pragma ide diagnostic ignored "UnusedParameter"
JNIEXPORT jobjectArray JNICALL Java_dev_sentchart_math_NativeMath_calculateFFT
        (JNIEnv* env, jclass cls, jdouble frequencySample, jdoubleArray signalArray) {
    jdouble* signalElements = env ->GetDoubleArrayElements(signalArray, JNI_FALSE);
    long int signalLength = env ->GetArrayLength(signalArray);

    long int fftSize = nextPowerOfTwo(signalLength);
    double* signal = new double[fftSize]; // array equal power of 2
    memset(signal, 0, sizeof(double)*fftSize); // filling array equal power of 2 zeros
    for (long int i = 0; i < signalLength; ++i) {
        signal[i] = signalElements[i]; // copying data in the new power of 2 array
    }
    double* data = new double[2*fftSize];
    memset(data, 0, sizeof(double)*(2*fftSize));
    hanning_window(signal, fftSize);
    for (long int i = 0; i < fftSize; ++i) {
        REAL(data, i) = signal[i];
    }
    gsl_fft_complex_radix2_forward(data, 1, fftSize);
    double* amplitude = new double [fftSize];
    for (long int i = 0; i < fftSize; ++i) {
        amplitude[i] = 2*(std::sqrt(pow(REAL(data, i),2) + pow(IMAG(data, i),2)))/(double)fftSize;
    }
    double* frequency = new double[fftSize];
    long int half_size = signalLength;
    for (long int i = 0; i < fftSize; ++i) {
        frequency[i] = ((((double) i) * frequencySample) / (double) fftSize);
    }
    long int cnt = half_size;
    double eps = 1e-3;
    while(cnt > 0){
        if(amplitude[cnt] > eps){
            break;
        }
        cnt--;
    }
    half_size = cnt + 10;
    double* half_freq = new double [half_size];
    double* half_ampl = new double [half_size];
    for(long int i = 0; i < half_size; ++i){
        half_freq[i] = frequency[i];
        half_ampl[i] = amplitude[i];
    }
    jdoubleArray frequencyArray = env ->NewDoubleArray((jsize) half_size);
    jdoubleArray amplitudeArray = env ->NewDoubleArray((jsize) half_size);
    env ->SetDoubleArrayRegion(frequencyArray, 0, (jsize) half_size, half_freq);
    env ->SetDoubleArrayRegion(amplitudeArray, 0, (jsize) half_size, half_ampl);
    jclass doubleClass = env ->FindClass("[D");
    jint arrayLength = 2;
    jobjectArray resultArray = env ->NewObjectArray(arrayLength, doubleClass, nullptr);
    env->SetObjectArrayElement(resultArray, 0, frequencyArray);
    env->SetObjectArrayElement(resultArray, 1, amplitudeArray);
    delete[] signal;
    delete[] data;
    delete[] amplitude;
    delete[] frequency;
    delete[] half_freq;
    delete[] half_ampl;
    return resultArray;
}



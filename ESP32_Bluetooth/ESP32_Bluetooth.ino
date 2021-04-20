#include "BluetoothSerial.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;

bool config = true;
float waarde = 0;
#define pot 32

void setup() {
  Serial.begin(9600);
  SerialBT.begin("ESP32"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  pinMode(pot, INPUT);
}

void loop() {
  waarde = analogRead(pot) / 204.75;
  SerialBT.print("Weight: ");
  SerialBT.print(waarde);
  SerialBT.print('\n');
  Serial.println(waarde);
  for(int sensor = 1; sensor <= 8; sensor++){
    SerialBT.print("Moisture: ");
    SerialBT.print(((int)waarde) % sensor);
    SerialBT.print('\n');
  }
  delay(1000);
}

import { WebPlugin } from '@capacitor/core';

import type { MockLocationPlugin } from './definitions';

export class MockLocationWeb extends WebPlugin implements MockLocationPlugin {
  check(_options?: { whiteList: string[] }): Promise<{ mockDetected: boolean, mocks?: { name: string, package: string }[] }> {
    return Promise.resolve({ mockDetected: false });
  }
}

export interface MockLocationPlugin {
  check(options?: { whiteList: string[] }): Promise<{ mockDetected: boolean, mocks?: { name: string, package: string }[] }>;
}

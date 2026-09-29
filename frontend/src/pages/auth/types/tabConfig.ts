export type TabConfig<T extends string> = {
  id: T;
  label: string;
  content: React.ReactNode;
}